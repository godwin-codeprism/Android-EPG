import React from 'react';
import {
  SafeAreaView,
  StatusBar,
  View,
  Text,
  Image,
  Dimensions
} from 'react-native';
import { EPGView, DataSource, ProgramRowView, ChannelView, HorizontalGridView, ShowView } from 'react-native-guide';


const channelsData = new DataSource(Array.from({ length: 50 }, (i, x) => `Channel ${x}`), item => item);
const showsData = new DataSource(Array.from({ length: 48 }, (i, x) => `Show ${x}`), item => item);

const timeBlocksData = new DataSource(new Array(24).fill('i').map((i, x) => x).reduce((a, i) => [...a, i + ":00", i + ":30"], []), item => item);

class App extends React.Component {
  render() {
    const wWidth = Dimensions.get('window').width;
    console.log(wWidth - (wWidth * .1), wWidth);
    return (
      <>
        <StatusBar barStyle="dark-content" />
        <SafeAreaView>
          <EPGView
            timelineDimensions={
              {
                width: wWidth - (wWidth * .1),
                height: 60,
              }
            }
            timeBlocks={timeBlocksData}
            renderTimeBlocks={({ item, index }) => (
              <View style={{ width: 135, height: 60, justifyContent: 'center', alignItems: 'center', backgroundColor: 'black', borderColor: 'grey', borderWidth: 1 }}>
                <Text style={{ color: 'white' }}>{item}</Text>
              </View>
            )}
            programRows={channelsData}
            renderProgramRows={({ item, index }) => (
              <ProgramRowView style={{ width: '100%', height: 40, backgroundColor: 'orange', flexDirection: 'row' }}>
                <ChannelView style={{ width: '10%', height: 40, backgroundColor: 'black', justifyContent: 'center', alignItems: 'center' }}>
                  <Image source={require('./assets/img/channel_logo_placeholder_72x54.png')} style={{ height: 15, width: 20 }} />
                  <Text style={{ color: 'white' }}>{item}</Text>
                </ChannelView>
                <View style={{ width: '90%', backgroundColor: 'black' }}>
                  <HorizontalGridView
                    showBlocks={showsData}
                    renderShowBlocks={({ item, index }) => (
                      <ShowView style={{ width: Math.round(Math.random() * (500 - 100 + 1) + 100), height: 40, justifyContent: 'center', alignItems: 'center' }}
                        showName={item}
                        marginLeft={3}
                        marginTop={3}
                        marginRight={3}
                        marginBottom={3}
                        activeBgColor='#4d4d4d'
                        bgColor="#282828"
                      />
                      // <ShowView style={{ width: 135, height: 40, justifyContent: 'center', alignItems: 'center' }} showName={item} />
                    )}
                  />
                </View>
              </ProgramRowView>
            )}
          />
        </SafeAreaView>
      </>
    )
  }
}

export default App;
