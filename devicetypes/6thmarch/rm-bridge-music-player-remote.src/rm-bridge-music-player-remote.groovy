/**
 *  RM Bridge Music Player Remote
 *
 *  Copyright 2015 Benjamin Yam
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
 */
 
 preferences {
       section("RM Bridge Server Configuration"){
       input "server", "text", title: "Server Address",
              description: "This is the domain or IP Address of the RM Bridge Server.", defaultValue: '',
              required: true, displayDuringSetup: true

       input "port", "text", title: "Port Number",
              description: "This is the port number of the RM Bridge Server.", defaultValue: '',
              required: true, displayDuringSetup: true
              
       input "username", "text", title: "Username",
              description: "This is the username for authentication for RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              
       input "passwd", "password", title: "Password",
              description: "This is the password for authentication for RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              }
              
       input "volumeUpCode", "text", title: "Volume Up Code",
              description: "This is the code to send to RM Bridge to adjust the volume up.",
              required: false, displayDuringSetup: true   
              
       input "volumeDownCode", "text", title: "Volume Down Code",
              description: "This is the code to send to RM Bridge to adjust the volume down.",
              required: false, displayDuringSetup: true
              
      input "muteCode", "text", title: "Mute Code",
              description: "This is the code to send to RM Bridge to mute the device.",
              required: false, displayDuringSetup: true
              
      input "btInputCode", "text", title: "Bluetooth Input Code",
              description: "This is the code to send to RM Bridge to activate Bluetooth Input.",
              required: false, displayDuringSetup: true
              
      input "usbInputCode", "text", title: "USB Input Code",
              description: "This is the code to send to RM Bridge to activate USB Input.",
              required: false, displayDuringSetup: true
              
      input "aux1InputCode", "text", title: "Aux 1 Input Code",
              description: "This is the code to send to RM Bridge to activate Aux 1 Input.",
              required: false, displayDuringSetup: true
              
     input "aux2InputCode", "text", title: "Aux 2 Input Code",
              description: "This is the code to send to RM Bridge to activate Aux 2 Input.",
              required: false, displayDuringSetup: true
              
     input "lineInputCode", "text", title: "Line Input Code",
              description: "This is the code to send to RM Bridge to activate line (3.5mm) Input.",
              required: false, displayDuringSetup: true
              
     input "playCode", "text", title: "Pause Code",
              description: "This is the code to send to RM Bridge to control play.",
              required: false, displayDuringSetup: true
              
         input "pauseCode", "text", title: "Pause Code",
              description: "This is the code to send to RM Bridge to control pause.",
              required: false, displayDuringSetup: true
    
    input "nextTrackCode", "text", title: "Next Track Code",
              description: "This is the code to send to RM Bridge to skip to next track.",
              required: false, displayDuringSetup: true
              
        input "previousTrackCode", "text", title: "Previous Track Code",
              description: "This is the code to send to RM Bridge to play the previous track.",
               required: false, displayDuringSetup: true
               
               input "powerOnCode", "text", title: "Power On Code",
              description: "This is the code to send to RM Bridge to switch on the music player.",
               required: false, displayDuringSetup: true
               
               input "powerOffCode", "text", title: "Power Off Code",
              description: "This is the code to send to RM Bridge to switch off the music player.",
               required: false, displayDuringSetup: true
     }
 
metadata {
	definition (name: "RM Bridge Music Player Remote", namespace: "6thmarch", author: "Benjamin Yam") {
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
   		 0.upto(numOfTimes, {
    	 	 api('volumeDown', [], {})
    	 })
     }
     else if(difference > 0 && numOfTimes > 0)
     {
     	log.info "volumeUp ${numOfTimes} times"
   		  0.upto(numOfTimes,{
   		  	api('volumeUp', [], {})
    	 })
     }


}



def makeJSONBroadlinkRMBridgeRequest(String code) {
        
    def params = [
        uri:  "http://$username:$passwd@$server:$port/code/",
        path: "$code",
        contentType: 'application/json'        
    ]
    try {
        httpGet(params) {resp ->
            log.debug "resp data: ${resp.data}"
            log.debug "code: ${resp.data.code}"
            log.debug "msg: ${resp.data.msg}"
        }
    } catch (e) {
        log.error "error: $e"
    }
}



// Methods stolen/modified from https://github.com/Dianoga
def api(method, args = [], success = {}) {
def methods = [
'powerOn': [code: "$powerOnCode", type: 'get'],
'powerOff': [code: "$powerOffCode", type: 'get'],
'playing': [code: "/playing", type: 'get'],
'play': [code: "$playCode", type: 'get'],
'pause': [code: "$pauseCode", type: 'get'],
'nextTrack': [code: "$nextTrackCode", type: 'get'],
'previousTrack': [code: "$previousTrackCode", type: 'get'],
'mute': [code: "$muteCode", type: 'get'],
'getVolume': [code: "/volume", type: 'get'],
'setVolume': [code: "/volume", type: 'get'],
'volumeDown': [code: "$volumeDownCode", type: 'get'],
'volumeUp': [code: "$volumeUpCode", type: 'get'],
'inputBT': [code: "$btInputCode", type: 'get'],
'inputLine': [code: "$lineInputCode", type: 'get'],
'inputAux1': [code: "$aux1InputCode", type: 'get'],
'inputAux2': [code: "$aux2InputCode", type: 'get'],
'inputUSB': [code: "$usbInputCode", type: 'get']


    ]
def request = methods.getAt(method)
    doRequest(request.code, args, request.type, success)
}
def doRequest(code, args, type, success) {
    log.debug "Calling $type : $code : $args"
    
def params = [
uri: "http://$username:$passwd@$server:$port/code/",
path: "$code",
headers: [
'Accept': "application/json"
        ],
body: args
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