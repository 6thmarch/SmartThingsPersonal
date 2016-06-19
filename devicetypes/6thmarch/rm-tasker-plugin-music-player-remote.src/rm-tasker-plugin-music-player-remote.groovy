/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Tasker Plugin Music Player Remote
 *	Version : 1.0.3
 * 
 * 	Description:
 * 		RM Tasker Plugin Music Player Remote is a SmartThings Device Type that allows you to turn on or off devices 
 * 		by sending Infrared or RF signal from Broadlink RM device to control your home devices.
 * 		This device requires RM Tasker Plugin to be installed and started in an android device within the 
 * 		same wi-fi network as the Broadlink RM device.
 * 
 * 	Requirements:
 * 		An android device (Android Box/Tablet/Phone) within the same wi-fi network as the Broadlink RM device, with RM Tasker Plugin HTTP Bridge installed and running.
 * 		Broadlink RM device
 * 		SmartThings Hub
 * 		Amazon Echo (Only for voice control)
 * 
 * 	References:
 * 		RM Tasker Plugin HTTP Bridge: https://play.google.com/store/apps/details?id=us.originally.tasker&hl=en
 * 		Broadlink RM Device: http://www.broadlink.com.cn/en/home-en.html
 *
 *	The original licensing applies, with the following exceptions:
 *		1.	These modifications may NOT be used without freely distributing all these modifications freely
 *			and without limitation, in source form.	 The distribution may be met with a link to source code
 *			with these modifications.
 *		2.	These modifications may NOT be used, directly or indirectly, for the purpose of any type of
 *			monetary gain.	These modifications may not be used in a larger entity which is being sold,
 *			leased, or anything other than freely given.
 *		3.	To clarify 1 and 2 above, if you use these modifications, it must be a free project, and
 *			available to anyone with "no strings attached."	 (You may require a free registration on
 *			a free website or portal in order to distribute the modifications.)
 *		4.	The above listed exceptions to the original licensing do not apply to the holder of the
 *			copyright of the original work.	 The original copyright holder can use the modifications
 *			to hopefully improve their original work.  In that event, this author transfers all claim
 *			and ownership of the modifications to "SmartThings."
 *
 *	Original Copyright information:
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *	The latest version of this file can be found at :
 *	https://github.com/6thmarch/SmartThingsPersonal
 *
 *  2016-02-29  V1.0.0  Initial release
 *	2016-03-07	V1.0.1	Switch from HTTP Get request to HTTP Post request
 *	2016-03-31	V1.0.2	Include user authentication
 *	2016-06-20  V1.0.3	Remove colons from MAC ID sent to bridge
 */
 
 preferences {
       section("RM Tasker Plugin HTTP Bridge Configuration"){
       input "server", "text", title: "Server Address",
              description: "This is the domain or IP Address of the HTTP Bridge Server.", defaultValue: '',
              required: true, displayDuringSetup: true

       input "port", "number", title: "Port Number",
              description: "This is the port number of the HTTP Bridge Server.", defaultValue: '',
              required: true, displayDuringSetup: true
              
       input "username", "text", title: "Username",
              description: "This is the username for authentication for RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              
       input "passwd", "password", title: "Password",
              description: "This is the password for authentication for RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              }
              
              input "deviceMacId", "text", title: "Device Mac ID",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx",
              required: true, displayDuringSetup: true
              
              
       input "volumeUpCode", "number", title: "Volume Up Code",
              description: "This is the code to send to RM Bridge to adjust the volume up.",
              required: false, displayDuringSetup: true   
              
       input "volumeDownCode", "number", title: "Volume Down Code",
              description: "This is the code to send to RM Bridge to adjust the volume down.",
              required: false, displayDuringSetup: true
              
      input "muteCode", "number", title: "Mute Code",
              description: "This is the code to send to RM Bridge to mute the device.",
              required: false, displayDuringSetup: true
              
      input "btInputCode", "number", title: "Bluetooth Input Code",
              description: "This is the code to send to RM Bridge to activate Bluetooth Input.",
              required: false, displayDuringSetup: true
              
      input "usbInputCode", "number", title: "USB Input Code",
              description: "This is the code to send to RM Bridge to activate USB Input.",
              required: false, displayDuringSetup: true
              
      input "aux1InputCode", "number", title: "Aux 1 Input Code",
              description: "This is the code to send to RM Bridge to activate Aux 1 Input.",
              required: false, displayDuringSetup: true
              
     input "aux2InputCode", "number", title: "Aux 2 Input Code",
              description: "This is the code to send to RM Bridge to activate Aux 2 Input.",
              required: false, displayDuringSetup: true
              
     input "lineInputCode", "number", title: "Line Input Code",
              description: "This is the code to send to RM Bridge to activate line (3.5mm) Input.",
              required: false, displayDuringSetup: true
              
     input "playCode", "number", title: "Play Code",
              description: "This is the code to send to RM Bridge to control play.",
              required: false, displayDuringSetup: true
              
         input "pauseCode", "number", title: "Pause Code",
              description: "This is the code to send to RM Bridge to control pause.",
              required: false, displayDuringSetup: true
    
    input "nextTrackCode", "number", title: "Next Track Code",
              description: "This is the code to send to RM Bridge to skip to next track.",
              required: false, displayDuringSetup: true
              
        input "previousTrackCode", "number", title: "Previous Track Code",
              description: "This is the code to send to RM Bridge to play the previous track.",
               required: false, displayDuringSetup: true
               
               input "powerOnCode", "number", title: "Power On Code",
              description: "This is the code to send to RM Bridge to switch on the music player.",
               required: false, displayDuringSetup: true
               
               input "powerOffCode", "number", title: "Power Off Code",
              description: "This is the code to send to RM Bridge to switch off the music player.",
               required: false, displayDuringSetup: true
     }
 
metadata {
	definition (name: "RM Tasker Plugin Music Player Remote", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Music Player"
        capability "Momentary"
        capability "Switch"
        capability "Refresh"
        capability "Actuator"
        capability "Button"
        capability "Sensor"
        capability "Switch Level"
	}

	tiles {
		standardTile("powerState", "device.switch", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "off", label: "OFF", action:"switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
            state "on", label: "ON", action:"switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
        }
        
        
        standardTile("volumeDown", "device.level", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Volume Down", icon: "st.thermostat.thermostat-down", action:"volumeDown"
		} 
        
 		standardTile("volumeUp", "device.level", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Volume Up", icon: "st.thermostat.thermostat-up", action:"volumeUp"
		} 
        
        controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 2, inactiveLabel: false, backgroundColor:"#ffe71e") {
            state "level", action:"switch level.setLevel"
        }
        
        valueTile("lValue", "device.level", decoration: "flat", inactiveLabel: true, width: 1, height: 1) {
        state "levelValue", label:'${currentValue}% ', unit:""
    	}
        
 		standardTile("nextTrack", "device.next", width: 1, height: 1, decoration: "flat") {
			state "default", icon: "st.sonos.next-btn", action: "nextTrack"
		} 
        
 		standardTile("previousTrack", "device.back", width: 1, height: 1, decoration: "flat") {
			state "default", icon: "st.sonos.previous-btn", action: "previousTrack"
		}
        
        standardTile("playPause", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	       state "default", action:"Music Player.playPause", icon: "st.sonos.play-btn"
        }
        
         standardTile("play", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	       state "default", action:"Music Player.play", icon: "st.sonos.play-btn"
        }
        
         standardTile("pause", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	       state "default", action:"Music Player.pause", icon: "st.sonos.pause-btn"
        }
        
        standardTile("mute", "device.mute", inactiveLabel:false, decoration:"flat") {
            state "default", label:"Mute",icon:"st.custom.sonos.muted", action:"Music Player.mute"
        }       
        
        standardTile("inputBTState", "device.button", inactiveLabel:false, decoration:"flat") {
            state "off", label: "Bluetooth", icon:"st.switches.switch.off", action:"inputBT", backgroundColor: "#ffffff"
            state "on", label: "Bluetooth", icon:"st.switches.switch.on", backgroundColor: "#79b821"
        }  
        
        standardTile("inputUSBState", "device.button", inactiveLabel:false, decoration:"flat") {
            state "off", label: "USB", icon:"st.switches.switch.off", action:"inputUSB", backgroundColor: "#ffffff"
            state "on", label: "USB", icon:"st.switches.switch.on", backgroundColor: "#79b821"
        }  
        
        standardTile("inputAux1State", "device.button", inactiveLabel:false, decoration:"flat") {
            state "off", label: "AUX1",  icon:"st.switches.switch.off", action:"inputAux1", backgroundColor: "#ffffff"
            state "on", label: "AUX1",  icon:"st.switches.switch.on", backgroundColor: "#79b821"
        }  
        
        standardTile("inputAux2State", "device.button", inactiveLabel:false, decoration:"flat") {
            state "off", label: "AUX2",  icon:"st.switches.switch.off", action:"inputAux2", backgroundColor: "#ffffff"
            state "on", label: "AUX2",  icon:"st.switches.switch.on", backgroundColor: "#79b821"
        }  
        
        standardTile("inputLineState", "device.button", inactiveLabel:false, decoration:"flat") {
            state "off", label: "LINE",  icon:"st.switches.switch.off", action:"inputLine", backgroundColor: "#ffffff"
            state "on", label: "LINE",  icon:"st.switches.switch.on", backgroundColor: "#79b821"
        }  
        
		main "powerState"
		details(["powerState","levelSliderControl", "lValue", "volumeDown","mute","volumeUp","previousTrack","playPause","nextTrack","inputBTState","inputUSBState","inputAux1State","inputAux2State","inputLineState"])
	}
    
    command "volumeDown"
    command "volumeUp"
    command "inputBT"
    command "inputUSB"
    command "inputAux1"
    command "inputAux2"
    command "inputLine"
    
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'volume' attribute
	// TODO: handle 'channel' attribute
	// TODO: handle 'power' attribute
	// TODO: handle 'picture' attribute
	// TODO: handle 'sound' attribute
	// TODO: handle 'movieMode' attribute

}



def on() {
	log.debug "Executing 'powerOn'"
	// TODO: handle 'powerOn' command
    api('powerOn', [], {
        sendEvent(name: 'powerState', value: 'on')
    })


}

def off() {
	log.debug "Executing 'powerOff'"
	// TODO: handle 'powerOff' command
    api('powerOff', [], {
        sendEvent(name: 'powerState', value: 'off')
    })

}

// handle commands
// MusicPlayer.mute
def mute() {
    //LOG("mute()")
     api('mute', [], {
        sendEvent(name: 'mute', value: 'muted')
    })
    
}

// MusicPlayer.unmute
def unmute() {
   // LOG("unmute()")

     api('mute', [], {
        sendEvent(name: 'mute', value: 'unmuted')
    })
}


def volumeUp() {
	log.debug "Executing 'volumeUp'"
	// TODO: handle 'volumeUp' command
    //makeJSONBroadlinkRMBridgeRequest("$volumeUpCode")
    api('volumeUp', [], {})
}

def volumeDown() {
	log.debug "Executing 'volumeDown'"
	// TODO: handle 'volumeDown' command
   // makeJSONBroadlinkRMBridgeRequest("$volumeDownCode")
  api('volumeDown', [], {})

}

def previousTrack() {
	log.debug "Executing 'previousTrack'"
	// TODO: handle 'previousTrack' command
 api('previousTrack', [], {})
}

def nextTrack() {
	log.debug "Executing 'volumeDown'"
	// TODO: handle 'nextTrack' command
 api('nextTrack', [], {})
}

def play() {
	log.debug "Executing 'play'"
	// TODO: handle 'play' command
    //api('play', [], {})
on()
}

def stop() {
	log.debug "Executing 'stop'"
	// TODO: handle 'stop' command
    //api('stop', [], {})
	off()
}

def pause() {
	log.debug "Executing 'pause'"
	// TODO: handle 'pause' command
    api('pause', [], {})
}

def inputBT(){
	log.debug "Executing 'InputBT'"
	// TODO: handle 'volumeDown' command
    api('inputBT', [], {
        sendEvent(name: 'inputBTState', value: 'on')
        sendEvent(name: 'inputUSBState', value: 'off')
        sendEvent(name: 'inputAux1State', value: 'off')
        sendEvent(name: 'inputAux2State', value: 'off')
        sendEvent(name: 'inputLineState', value: 'off')
    })
}

def inputUSB(){
	log.debug "Executing 'InputUSB'"
	// TODO: handle 'volumeDown' command
    api('inputUSB', [], {
        sendEvent(name: 'inputUSBState', value: 'on')
        sendEvent(name: 'inputAux1State', value: 'off')
        sendEvent(name: 'inputAux2State', value: 'off')
        sendEvent(name: 'inputLineState', value: 'off')
        sendEvent(name: 'inputBTState', value: 'off')
    })
}

def inputAux1(){
	log.debug "Executing 'InputAux1'"
	// TODO: handle 'volumeDown' command
    api('inputAux1', [], {
        sendEvent(name: 'inputAux1State', value: 'on')
        sendEvent(name: 'inputAux2State', value: 'off')
        sendEvent(name: 'inputLineState', value: 'off')
        sendEvent(name: 'inputBTState', value: 'off')
        sendEvent(name: 'inputUSBState', value: 'off')
    })
}

def inputAux2(){
	log.debug "Executing 'InputAux2'"
	// TODO: handle 'volumeDown' command
    api('inputAux2', [], {
        sendEvent(name: 'inputAux2State', value: 'on')
        sendEvent(name: 'inputLineState', value: 'off')
        sendEvent(name: 'inputBTState', value: 'off')
        sendEvent(name: 'inputUSBState', value: 'off')
        sendEvent(name: 'inputAux1State', value: 'off')
    })
}

def inputLine(){
	log.debug "Executing 'InputLine'"
	// TODO: handle 'volumeDown' command
    api('inputLine', [], {
        sendEvent(name: 'inputLineState', value: 'off')
        sendEvent(name: 'inputBTState', value: 'off')
        sendEvent(name: 'inputUSBState', value: 'off')
        sendEvent(name: 'inputAux1State', value: 'off')
        sendEvent(name: 'inputAux2State', value: 'off')
    })
}

def setLevel(Double newlevel){
	def maxLimit = 35
	def difference = (newlevel - device.currentValue("level"))
    def numOfTimes = ( (difference.abs()/100) * maxLimit).round()
         sendEvent(name:"level",value:newlevel)


	
    if(difference < 0 && numOfTimes > 0 )
    {
         log.info "volumeDown ${numOfTimes} times"
   		
      	api('volumeDown', ['repeat' : numOfTimes], {})


     }
     else if(difference > 0 && numOfTimes > 0)
     {
     	log.info "volumeUp ${numOfTimes} times"
   		
        api('volumeUp', ['repeat' : numOfTimes], {})
     }


}

def api(method, args = [], success = {}) {

def methods = [
'powerOn': [code: "$powerOnCode", type: 'post'],
'powerOff': [code: "$powerOffCode", type: 'post'],
'playing': [code: "/playing", type: 'post'],
'play': [code: "$playCode", type: 'post'],
'pause': [code: "$pauseCode", type: 'post'],
'nextTrack': [code: "$nextTrackCode", type: 'post'],
'previousTrack': [code: "$previousTrackCode", type: 'post'],
'mute': [code: "$muteCode", type: 'post'],
'getVolume': [code: "/volume", type: 'post'],
'setVolume': [code: "/volume", type: 'post'],
'volumeDown': [code: "$volumeDownCode", type: 'post'],
'volumeUp': [code: "$volumeUpCode", type: 'post'],
'inputBT': [code: "$btInputCode", type: 'post'],
'inputLine': [code: "$lineInputCode", type: 'post'],
'inputAux1': [code: "$aux1InputCode", type: 'post'],
'inputAux2': [code: "$aux2InputCode", type: 'post'],
'inputUSB': [code: "$usbInputCode", type: 'post']
    ]
def request = methods.getAt(method)
    doRequest(request.code, args, request.type, success)
}
def doRequest(code, args, type, success) {
    log.debug "Calling $type : $code : $args"
    def repeatVal = 1
    if(args['repeat']){
    	repeatVal = args['repeat']
    }
    log.debug "repeatVal: $repeatVal"
def params = [
uri: "http://$server:$port",
path: "/send",
headers: [
'Accept': "application/json",
'Authorization' : 'Basic '+"$username:$passwd".bytes.encodeBase64()
        ],
query: ['deviceMac' : deviceMacId.replaceAll(":",""), 'codeId' : code, 'repeat': repeatVal] //args 
    ]
	if(type == 'post') {
       httpPostJson(params, success)
       log.debug success
    } else if (type == 'get') {
       httpGet(params, success)
       log.debug success
    } else if (type == 'put') {
    	httpPutJson(params, success)
        log.debug success
    }
}

def refresh() {
    log.info "refresh"
}