# react-native-gr-view

## Getting started

`$ npm install react-native-gr-view --save`

### Mostly automatic installation

`$ react-native link react-native-gr-view`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-gr-view` and add `GrView.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libGrView.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.GrViewPackage;` to the imports at the top of the file
  - Add `new GrViewPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-gr-view'
  	project(':react-native-gr-view').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-gr-view/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-gr-view')
  	```


## Usage
```javascript
import GrView from 'react-native-gr-view';

// TODO: What to do with the module?
GrView;
```
