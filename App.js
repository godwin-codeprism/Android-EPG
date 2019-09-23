import React, { Fragment } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TouchableOpacity,
  FlatList,
  Dimensions,
  requireNativeComponent
} from 'react-native';

import RecyclerView, { DataSource } from 'react-native-gr-view';

const GVerticalGridView = requireNativeComponent("GVerticalGridView");
const EPGCell = requireNativeComponent("EPGCell");

const App = () => {
  // Take an array as data
  var rawdata = new Array(3000).fill('i').map((i, x) => ({ id: x, text: "Show" + x }));
  const wWidth = Dimensions.get('window').width;
  const wHeight = Dimensions.get('window').height;

  // Wrap your data in a DataSource.
  // The second argument is the 'keyExtractor' function that returns the unique key of the item.
  // var dataSource = new DataSource([{ id: 1, text: "test" }], (item, index) => item.id);
  var dataSource = new DataSource(rawdata, (item, index) => item.id);

  return (
    <Fragment>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <View style={{ flexDirection: 'column', height: '100%' }}>

          {/* Native Recycler View */}

          <View style={{ width: '100%', height: '25%' }}>
            <Text>Native Recycler View - {rawdata.length} items</Text>
            {/* <RecyclerView
                style={{ width: '100%', height: '100%' }}
                // dataSource={new DataSource([{ id: Math.round(Math.random()), text: "text" }], (item, index) => item.id)}
                dataSource={dataSource}
                renderItem={({ item, index }) => (
                  <TouchableOpacity style={{ width: 100, height: 50, backgroundColor: 'green' }}>
                    <Text style={{ color: 'white' }}>{item.text} - {index}</Text>
                  </TouchableOpacity>
                )} /> */}
            <GVerticalGridView style={{ width: 500, height: 400, backgroundColor: 'red' }}>
              {
                Array.from({ length: 10 }, () => 0).map((i,x) => (
                  <EPGCell style={{ width: 125, height: 70 }} key={x}/>
                ))
              }
            </GVerticalGridView>
          </View>

          {/* React Native FlatList */}

          {/* <View style={{ width: '100%', height: '25%',  marginTop: 50 }}>
            <Text>React Native FlatList - {rawdata.length} items</Text>
            <FlatList
              style={{height:50}}
              horizontal
              data={rawdata}
              keyExtractor={item => item.text}
              renderItem={({ item, index }) => (
                <TouchableOpacity style={{ width: 100, height: 50, backgroundColor: 'orange' }}>
                  <Text style={{ color: 'white' }}>{item.text} - {index}</Text>
                </TouchableOpacity>
              )}
            />
          </View>
           */}
          {/* React Native Scroll View */}

          {/* <View style={{ width: '100%', height: '25%', marginTop: 50 }}>
            <Text>React Native Scroll View - {rawdata.length} items</Text>
            <ScrollView horizontal>
              {
                rawdata.map((item, index) => (
                  <TouchableOpacity style={{ width: 100, height: 50, backgroundColor: 'red' }}>
                    <Text style={{ color: 'white' }}>{item.text} - {index}</Text>
                  </TouchableOpacity>
                ))
              }
            </ScrollView>
          </View> */}
        </View>
      </SafeAreaView>
    </Fragment>
  );
};

export default App;
