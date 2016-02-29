/**
 *  RM Bridge Fan Remote
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
       
               
               input "powerOnCode", "text", title: "Power On Code",
              description: "This is the code to send to RM Bridge to switch on the fan.",
               required: true, displayDuringSetup: true
               input "powerOffCode", "text", title: "Power Off Code",
              description: "This is the code to send to RM Bridge to switch off the fan.",
               required: true, displayDuringSetup: true
               input "fanSpeedCode", "text", title: "Fan Speed Code",
              description: "This is the code to send to RM Bridge to adjust the speed of the fan.",
               required: false, displayDuringSetup: true
               input "fanOscillateCode", "text", title: "Fan Oscillation Code",
              description: "This is the code to send to RM Bridge to adjust the oscillation of the fan.",
               required: false, displayDuringSetup: true
               input "fanTimerCode", "text", title: "Fan Timer Code",
              description: "This is the code to send to RM Bridge to adjust the timer of the fan.",
               required: false, displayDuringSetup: true
     }
 
metadata {
	definition (name: "RM Bridge Fan Remote", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Momentary"
        capability "Switch"
        capability "Refresh"
        capability "Actuator"
        capability "Button"
	}

	tiles {
		standardTile("powerState", "device.switch", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "off", label: "OFF", action:"switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
            state "on", label: "ON", action:"switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
        }
        
        
        
        standardTile("speed", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "Speed",  icon:"st.switches.switch.off", action:"changeSpeed", backgroundColor: "#ffffff"
        }  
        standardTile("oscillate", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "Oscillate",  icon:"st.switches.switch.off", action:"changeOscillate", backgroundColor: "#ffffff"
        }  
        standardTile("timer", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "Timer",  icon:"st.switches.switch.off", action:"changeTimer", backgroundColor: "#ffffff"
        }  
        
		main "powerState"
		details(["powerState", "speed","oscillate","timer"])
	}
    
    command "changeSpeed"
    command "changeOscillate"
    command "changeTimer"
    
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

def changeSpeed(){
 api('changeSpeed', [], {})
}

def changeOscillate(){
 api('ChangeOscillation', [], {})
}

def changeTimer(){
 api('changeTimer', [], {})
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
'changeSpeed': [code: "$fanSpeedCode", type: 'get'],
'changeOscillation': [code: "$fanOscillateCode", type: 'get'],
'changeTimer': [code: "$fanTimerCode", type: 'get']


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