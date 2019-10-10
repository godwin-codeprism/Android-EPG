import React from 'react';
import {
  SafeAreaView,
  StatusBar,
  View,
  Text,
  TouchableOpacity
} from 'react-native';
import { EPGView, DataSource, ProgramRowView, ChannelView, HorizontalGridView, ShowView } from 'react-native-guide';


const channelsData = new DataSource(Array.from({ length: 50 }, (i, x) => `Channel ${x}`), item => item);
const showsData = new DataSource(Array.from({ length: 48 }, (i, x) => `Show ${x}`), item => item);

const timeBlocksData = new DataSource(new Array(24).fill('i').map((i, x) => x).reduce((a, i) => [...a, i + ":00", i + ":30"], []), item => item);

class App extends React.Component {
  render() {
    return (
      <>
        <StatusBar barStyle="dark-content" />
        <SafeAreaView>
          <EPGView
            timeBlocks={timeBlocksData}
            renderTimeBlocks={({ item, index }) => (
              <View style={{ width: 135, height: 60, justifyContent: 'center', alignItems: 'center', backgroundColor: 'black', borderColor: 'grey', borderWidth: 1 }}>
                <Text style={{ color: 'white' }}>{item}</Text>
              </View>
            )}
            programRows={channelsData}
            renderProgramRows={({ item, index }) => (
              <ProgramRowView style={{ width: '100%', height: 60, backgroundColor: 'orange', flexDirection: 'row' }}>
                <ChannelView style={{ width: '17%', height: 60, backgroundColor: 'purple' }}>
                  <Text style={{ color: 'white' }}>{item}</Text>
                  <Text style={{ color: 'white' }}>Channel Image goes here</Text>
                </ChannelView>
                <View style={{ width: '83%', backgroundColor: 'green' }}>
                  <HorizontalGridView
                    showBlocks={showsData}
                    renderShowBlocks={({ item, index }) => (
                      <ShowView style={{ width: 135, height: 60, justifyContent: 'center', alignItems: 'center' }} showName={item} />
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
