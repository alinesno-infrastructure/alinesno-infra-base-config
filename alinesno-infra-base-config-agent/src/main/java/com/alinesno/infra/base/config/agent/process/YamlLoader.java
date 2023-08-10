package com.alinesno.infra.base.config.agent.process;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.boot.origin.TextResourceOrigin.Location;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * 参考Springboot yamlLoader写法处理的加载器，用于yaml文件的加载过程。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class YamlLoader extends YamlProcessor {

	private final Resource resource;

	public YamlLoader(Resource resource) {
		this.resource = resource;
		setResources(resource);
	}

	@Override
	public Yaml createYaml() {
		LoaderOptions loaderOptions = new LoaderOptions();
		loaderOptions.setAllowDuplicateKeys(false);
		loaderOptions.setMaxAliasesForCollections(Integer.MAX_VALUE);
		loaderOptions.setAllowRecursiveKeys(true);
		loaderOptions.setCodePointLimit(Integer.MAX_VALUE);
		return createYaml(loaderOptions);
	}

	private Yaml createYaml(LoaderOptions loaderOptions) {
		BaseConstructor constructor = new OriginTrackingConstructor(loaderOptions);
		DumperOptions dumperOptions = new DumperOptions();
		Representer representer = new Representer(dumperOptions);
		NoTimestampResolver resolver = new NoTimestampResolver();
		return new Yaml(constructor, representer, dumperOptions, loaderOptions, resolver);
	}

	public List<Map<String, Object>> load() {
		List<Map<String, Object>> result = new ArrayList<>();
		process((properties, map) -> result.add(getFlattenedMap(map)));
		return result;
	}

	private class OriginTrackingConstructor extends SafeConstructor {

		OriginTrackingConstructor(LoaderOptions loadingConfig) {
			super(loadingConfig);
		}

		@Override
		public Object getData() throws NoSuchElementException {
			Object data = super.getData();
			if (data instanceof CharSequence charSequence && charSequence.isEmpty()) {
				return null;
			}
			return data;
		}

		@Override
		protected Object constructObject(Node node) {
			if (node instanceof CollectionNode && ((CollectionNode<?>) node).getValue().isEmpty()) {
				return constructTrackedObject(node, super.constructObject(node));
			}
			if (node instanceof ScalarNode) {
				if (!(node instanceof KeyScalarNode)) {
					return constructTrackedObject(node, super.constructObject(node));
				}
			}
			if (node instanceof MappingNode mappingNode) {
				replaceMappingNodeKeys(mappingNode);
			}
			return super.constructObject(node);
		}

		private void replaceMappingNodeKeys(MappingNode node) {
			List<NodeTuple> newValue = new ArrayList<>();
			node.getValue().stream().map(KeyScalarNode::get).forEach(newValue::add);
			node.setValue(newValue);
		}

		private Object constructTrackedObject(Node node, Object value) {
			Origin origin = getOrigin(node);
			return OriginTrackedValue.of(getValue(value), origin);
		}

		private Object getValue(Object value) {
			return (value != null) ? value : "";
		}

		private Origin getOrigin(Node node) {
			Mark mark = node.getStartMark();
			Location location = new Location(mark.getLine(), mark.getColumn());
			return new TextResourceOrigin(YamlLoader.this.resource, location);
		}

	}

	private static class KeyScalarNode extends ScalarNode {

		KeyScalarNode(ScalarNode node) {
			super(node.getTag(), node.getValue(), node.getStartMark(), node.getEndMark(), node.getScalarStyle());
		}

		static NodeTuple get(NodeTuple nodeTuple) {
			Node keyNode = nodeTuple.getKeyNode();
			Node valueNode = nodeTuple.getValueNode();
			return new NodeTuple(KeyScalarNode.get(keyNode), valueNode);
		}

		private static Node get(Node node) {
			if (node instanceof ScalarNode scalarNode) {
				return new KeyScalarNode(scalarNode);
			}
			return node;
		}

	}

	private static class NoTimestampResolver extends Resolver {

		@Override
		public void addImplicitResolver(Tag tag, Pattern regexp, String first, int limit) {
			if (tag == Tag.TIMESTAMP) {
				return;
			}
			super.addImplicitResolver(tag, regexp, first, limit);
		}

	}

}
