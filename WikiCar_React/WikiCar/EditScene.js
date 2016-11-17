import React, {Component} from 'react';
import {
   TouchableHighlight,
   AppRegistry,
   StyleSheet,
   Text,
   TextInput,
   View,
  TouchableOpacity
} from 'react-native';

class EditScene extends Component {
  constructor(props){
    super(props);
    this.state = { text: "this.props.name" };
  }


  render() {
    return (
      <View style={styles.container}>
      <Text> Name: {this.props.name}</Text>
      <Text>New value: </Text>
      <TextInput
      style={{height: 40, borderColor: 'gray', borderWidth: 1}}
      onChangeText={(text) => this.setState({text})}

        />

        <TouchableOpacity onPress={()=> this.props.navigator.push({index: 0,
                  passProps:{
                      Newname: this.state.text,
                      id: this.props.id
                   }})}>

     <View>
        <Text style={styles.item}>Submit changes</Text>
        </View>
   </TouchableOpacity>

      </View>
    );
  }
}

var styles = StyleSheet.create({
  container:{
    flex:1,
    padding: 10,
    paddingTop:70,
  },
});

export default EditScene;
