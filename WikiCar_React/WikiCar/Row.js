import React from 'react';
import { 
  View, 
  Text, 
  StyleSheet, 
  Image, 
  TouchableHighlight,
  Linking} from 'react-native';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 12,
    flexDirection: 'row',
    alignItems: 'center',
  },
  text: {
    marginLeft: 12,
    fontSize: 16,
  },
  photo: {
    height: 40,
    width: 40,
    borderRadius: 20,
  },
});

var myComponent = React.createComponent({

    getInitialState: function () {
        return {
            showCancel: false,
        };
    },

    toggleCancel: function () {
        this.setState({
            showCancel: !this.state.showCancel
        });
    },

    _renderCancel: function () {
        if (this.state.showCancel) {
            return (
                <TouchableHighlight 
                    onPress={this.toggleCancel()}>
                    <View>
                        <Text style={styles.cancelButtonText}>Cancel</Text>
                    </View>
                </TouchableHighlight>
            );
        } else {
            return null;
        }
    },

    render: function () {
        return (
            <TextInput
                onFocus={this.toggleCancel()}
                onChangeText={(text) => this.doSearch({input: text})} />
        this._renderCancel()       
        );
    }

});


const Row = (props) => (
  <TouchableHighlight 
  onPress={

    () => {
      myComponent.toggleCancel
    }

  }

  onLongPress={ () =>{
     Linking.openURL('mailto:?subject=Checkout this car info!&body=Hey, checkout the info for this awesome car!' + 
      `${props.name.make}`).catch(err => console.error('An error occurred', err)); }} underlayColor="#e2e2e2">
    
    <View style={styles.container}>
      <Text><Text style={styles.text}>
        {`${props.name.make} ${props.name.model}`}
      </Text></Text>
    </View>
 </TouchableHighlight>
);

export default Row;