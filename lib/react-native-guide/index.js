import React from 'react';
import ReactNative, { requireNativeComponent, StyleSheet, UIManager, NativeModules, processColor } from 'react-native';
import DataSource from './datasource';

const { Guide } = NativeModules;

var nativeOnlyProps = {
  nativeOnly: {
    onVisibleItemsChange: true,
    itemCount: true
  }
};


class EPGView extends React.Component {

  render() {
    const { timelineDimensions } = this.props;
    const _props = { ...this.props };
    delete _props.timelineDimensions;
    return (
      <GuideView style={styles.cover} timelineDimensions={timelineDimensions}>
        <RecyclerViewComponent
          dataSource={this.props.programRows}
          renderItem={this.props.renderProgramRows}
          {..._props}
        />
        <TimelineComponent
          timeBlocks={this.props.timeBlocks}
          renderTimeBlocks={this.props.renderTimeBlocks}
        />
      </GuideView>
    )
  }
}


class RecyclerViewComponent extends React.Component {

  static defaultProps = {
    dataSource: new DataSource([], (item, i) => i),
    initialListSize: 10,
    windowSize: 30,
    inverted: false,
    itemAnimatorEnabled: true,
  }

  _dataSourceListener = {
    onUnshift: () => {
      this._notifyItemRangeInserted(0, 1);
      this._shouldUpdateAll = true;
    },

    onPush: () => {
      const { dataSource } = this.props;
      this._notifyItemRangeInserted(dataSource.size(), 1);
      this._shouldUpdateAll = true;
    },

    onMoveUp: (position) => {
      this._notifyItemMoved(position, position - 1);
      this._shouldUpdateAll = true;
    },

    onMoveDown: (position) => {
      this._notifyItemMoved(position, position + 1);
      this._shouldUpdateAll = true;
    },

    onSplice: (start, deleteCount, ...items) => {
      if (deleteCount > 0) {
        this._notifyItemRangeRemoved(start, deleteCount);
      }
      if (items.length > 0) {
        this._notifyItemRangeInserted(start, items.length);
      }
      this._shouldUpdateAll = true;
    },

    onSet: (index, item) => {
      this._shouldUpdateKeys.push(this.props.dataSource.getKey(item, index));
      this.forceUpdate();
    },

    onSetDirty: () => {
      this._shouldUpdateAll = true;
      this.forceUpdate();
    }
  }

  constructor(props) {
    super(props);

    const {
      dataSource,
      initialListSize,
      initialScrollIndex
    } = this.props;

    dataSource._addListener(this._dataSourceListener);

    var visibleRange = initialScrollIndex >= 0 ?
      [initialScrollIndex, initialScrollIndex + initialListSize]
      : [0, initialListSize];

    this.state = {
      firstVisibleIndex: visibleRange[0],
      lastVisibleIndex: visibleRange[1],
      itemCount: dataSource.size()
    };

    this._shouldUpdateAll = true;
    this._shouldUpdateKeys = [];
  }

  componentWillUnmount() {
    const { dataSource } = this.props;
    if (dataSource) {
      dataSource._removeListener(this._dataSourceListener);
    }
  }

  componentDidMount() {
    const { initialScrollIndex, initialScrollOffset } = this.props;
    if (initialScrollIndex) {
      this.scrollToIndex({
        animated: false,
        index: initialScrollIndex,
        viewPosition: 0,
        viewOffset: initialScrollOffset
      });
    }

    this._shouldUpdateAll = false;
    this._shouldUpdateKeys = [];
  }

  componentDidUpdate(prevProps, prevState) {
    const { dataSource } = this.props;
    if (prevProps.dataSource.toString() !== dataSource.toString()) {
      prevProps.dataSource._removeListener(this._dataSourceListener);
      dataSource._addListener(this._dataSourceListener);
      this._notifyDataSetChanged(dataSource.size());
    }
    this._shouldUpdateAll = false;
    this._shouldUpdateKeys = [];
  }

  render() {
    const {
      dataSource,
      renderItem,
      ListHeaderComponent,
      ListFooterComponent,
      ListEmptyComponent,
      ItemSeparatorComponent,
      inverted,
      horizontal,
      timeline,
      ...rest
    } = this.props;

    const itemCount = dataSource.size();
    const end = itemCount - 1;
    var stateItemCount = this.state.itemCount;

    var body = [];
    var itemRangeToRender = this._calcItemRangeToRender(this.state.firstVisibleIndex, this.state.lastVisibleIndex);

    if (ListHeaderComponent) {
      var headerElement = React.isValidElement(ListHeaderComponent)
        ? ListHeaderComponent
        : <ListHeaderComponent />;
    }

    if (ListFooterComponent) {
      var footerElement = React.isValidElement(ListFooterComponent)
        ? ListFooterComponent
        : <ListFooterComponent />;
    }

    if (ItemSeparatorComponent) {
      var separatorElement = React.isValidElement(ItemSeparatorComponent)
        ? ItemSeparatorComponent
        : <ItemSeparatorComponent />;
    }

    if (itemCount > 0) {
      for (var i = itemRangeToRender[0]; i < itemRangeToRender[1]; i++) {
        let item = dataSource.get(i);
        let itemKey = dataSource.getKey(item, i);
        let shouldUpdate = this._needsItemUpdate(itemKey);
        let isFirst = i == 0;
        let isLast = i == end;
        let header = inverted ? (isLast && footerElement) : (isFirst && headerElement);
        let footer = inverted ? (isFirst && headerElement) : (isLast && footerElement);
        let separator = inverted ? (!isFirst && separatorElement) : (!isLast && separatorElement);
        const itemStyles = renderItem({ item }).props.style;
        const itemWidth = itemStyles ? itemStyles.width : null;
        const itemHeight = itemStyles ? itemStyles.height : null;
        body.push(
          <GridItemComponent
            key={itemKey}
            style={[styles.absolute, itemWidth ? { width: itemWidth } : null, itemHeight ? { height: itemHeight } : null]}
            itemIndex={i}
            shouldUpdate={shouldUpdate}
            dataSource={dataSource}
            renderItem={renderItem}
            header={header}
            separator={separator}
            footer={footer} />
        );
      }
    } else if (ListEmptyComponent) {
      var emptyElement = React.isValidElement(ListEmptyComponent)
        ? ListEmptyComponent
        : <ListEmptyComponent />;

      body.push(
        <GridItemComponent
          style={styles.absolute}
          key="$empty"
          itemIndex={0}
          shouldUpdate={true}
          dataSource={dataSource}
          renderItem={() => emptyElement}
          header={headerElement}
          footer={footerElement} />
      );

      stateItemCount = 1;
    }

    return (
      timeline ?
        <TimelineNativeView
          style={{ flex: 1 }}
          {...rest}
          itemCount={stateItemCount}
          onVisibleItemsChange={this._handleVisibleItemsChange}
          inverted={inverted}>
          {body}
        </TimelineNativeView> :
        horizontal ?
          <ChildGridNativeView
            style={{ flex: 1 }}
            {...rest}
            itemCount={stateItemCount}
            onVisibleItemsChange={this._handleVisibleItemsChange}
            inverted={inverted}>
            {body}
          </ChildGridNativeView>
          :
          <ParentGridView
            style={{ flex: 1 }}
            {...rest}
            itemCount={stateItemCount}
            onVisibleItemsChange={this._handleVisibleItemsChange}
            inverted={inverted}>
            {body}
          </ParentGridView>
    );
  }

  scrollToEnd({ animated = true, velocity } = {}) {
    this.scrollToIndex({
      index: this.props.dataSource.size() - 1,
      animated,
      velocity
    });
  }

  scrollToIndex = ({ animated = true, index, velocity, viewPosition, viewOffset }) => {
    index = Math.max(0, Math.min(index, this.props.dataSource.size() - 1));

    if (animated) {
      UIManager.dispatchViewManagerCommand(
        ReactNative.findNodeHandle(this),
        UIManager.GRVBackendScrollView.Commands.scrollToIndex,
        [animated, index, velocity, viewPosition, viewOffset],
      );
    } else {
      this.setState({
        firstVisibleIndex: index,
        lastVisibleIndex: index + (this.state.lastVisibleIndex - this.state.firstVisibleIndex)
      }, () => {
        UIManager.dispatchViewManagerCommand(
          ReactNative.findNodeHandle(this),
          UIManager.GRVBackendScrollView.Commands.scrollToIndex,
          [animated, index, velocity, viewPosition, viewOffset],
        );
      });
    }
  }

  _needsItemUpdate(itemKey) {
    return this._shouldUpdateAll || this._shouldUpdateKeys.includes(itemKey);
  }

  _handleVisibleItemsChange = ({ nativeEvent }) => {
    var firstIndex = nativeEvent.firstIndex;
    var lastIndex = nativeEvent.lastIndex;

    this.setState({
      firstVisibleIndex: firstIndex,
      lastVisibleIndex: lastIndex,
    });

    const { onVisibleItemsChange } = this.props;
    if (onVisibleItemsChange) {
      onVisibleItemsChange(nativeEvent);
    }
  }

  _calcItemRangeToRender(firstVisibleIndex, lastVisibleIndex) {
    const { dataSource, windowSize } = this.props;
    var count = dataSource.size();
    var from = Math.min(count, Math.max(0, firstVisibleIndex - windowSize));
    var to = Math.min(count, lastVisibleIndex + windowSize);
    return [from, to];
  }

  _notifyItemMoved(currentPosition, nextPosition) {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.GRVBackendScrollView.Commands.notifyItemMoved,
      [currentPosition, nextPosition],
    );
    this.forceUpdate();
  }

  _notifyItemRangeInserted(position, count) {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.GRVBackendScrollView.Commands.notifyItemRangeInserted,
      [position, count],
    );

    const { firstVisibleIndex, lastVisibleIndex, itemCount } = this.state;

    if (itemCount == 0) {
      this.setState({
        itemCount: this.props.dataSource.size(),
        firstVisibleIndex: 0,
        lastVisibleIndex: this.props.initialListSize
      });
    } else {
      if (position <= firstVisibleIndex) {
        this.setState({
          firstVisibleIndex: this.state.firstVisibleIndex + count,
          lastVisibleIndex: this.state.lastVisibleIndex + count,
        });
      } else {
        this.forceUpdate();
      }
    }
  }

  _notifyItemRangeRemoved(position, count) {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.GRVBackendScrollView.Commands.notifyItemRangeRemoved,
      [position, count],
    );
    this.forceUpdate();
  }

  _notifyDataSetChanged(itemCount) {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.GRVBackendScrollView.Commands.notifyDataSetChanged,
      [itemCount],
    );
    this.setState({
      itemCount
    });
  }
}

class GridItemComponent extends React.Component {
  shouldComponentUpdate(nextProps) {
    if (
      (nextProps.itemIndex !== this.props.itemIndex) ||
      (nextProps.header !== this.props.header) ||
      (nextProps.footer !== this.props.footer) ||
      (nextProps.separator !== this.props.separator) ||
      (nextProps.shouldUpdate)
    ) {
      return true;
    }

    return false;
  }

  render() {
    const { style, itemIndex, dataSource, renderItem, header, separator, footer } = this.props;
    const element = renderItem({
      item: dataSource.get(itemIndex),
      index: itemIndex
    });

    return (
      <GridItem
        style={style}
        itemIndex={itemIndex}>
        {header}
        {element}
        {separator}
        {footer}
      </GridItem>
    );
  }
}

class ShowView extends React.Component {
  render() {
    const { activeBgColor, bgColor } = this.props;
    return (
      <NativeShowView {...this.props} activeBgColor={processColor(activeBgColor)} bgColor={processColor(bgColor)} />
    )
  }
}

const HorizontalGridView = (props) => <RecyclerViewComponent horizontal dataSource={props.showBlocks} renderItem={props.renderShowBlocks} {...props} />;

const TimelineComponent = (props) => <RecyclerViewComponent timeline dataSource={props.timeBlocks} renderItem={props.renderTimeBlocks} {...props} />;

const ExpandedView = (props) => <NativeExpandedView {...props}/>;

const GuideView = requireNativeComponent('GuideView');
const ParentGridView = requireNativeComponent('ParentGridView', RecyclerViewComponent, nativeOnlyProps);
const GridItem = requireNativeComponent('GridItem');
const ProgramRowView = requireNativeComponent('ProgramRowView');
const ChannelView = requireNativeComponent('ChannelView');
const ChildGridNativeView = requireNativeComponent('ChildGridView');
const NativeShowView = requireNativeComponent('ShowView');
const TimelineNativeView = requireNativeComponent('TimelineView');
const NativeExpandedView = requireNativeComponent('ExpandedView');

const styles = StyleSheet.create({
  cover: { width: '100%', height: '100%' },
  absolute: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0
  },
})

export default Guide;

export {
  EPGView,
  ShowView,
  ProgramRowView,
  ChannelView,
  HorizontalGridView,
  ExpandedView,
  DataSource
}
