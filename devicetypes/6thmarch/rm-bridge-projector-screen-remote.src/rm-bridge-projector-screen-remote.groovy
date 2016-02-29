/**
 *  RM Bridge Projector Screen Remote
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
       
               
               input "projectorScreenDrawCode", "text", title: "Projector Screen Draw Code",
              description: "This is the code to send to RM Bridge to draw the projector screen.",
               required: true, displayDuringSetup: true
              
              input "projectorScreenRetractCode", "text", title: "Projector Screen Retract Code",
              description: "This is the code to send to RM Bridge to retract the projector screen.",
               required: true, displayDuringSetup: true
              
              input "projectorScreenPauseCode", "text", title: "Projector Screen Pause Code",
              description: "This is the code to send to RM Bridge to pause the projector screen.",
               required: false, displayDuringSetup: true
               
     }
 
metadata {
	definition (name: "RM Bridge Projector Screen Remote", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Momentary"
        capability "Switch"
        capability "Refresh"
        capability "Actuator"
        capability "Button"
	}

	tiles {
		standardTile("drawnState", "device.switch", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "off", label: "Retracted", action:"switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
            state "on", label: "Drawn", action:"switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
        }
        
        
        
        standardTile("pause", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "pause",  icon:"st.switches.switch.off", action:"pause", backgroundColor: "#ffffff"
        }  
      
        
		main "drawnState"
		details(["drawnState", "pause"])
	}
    
    command "pause"
    
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
        sendEvent(name: 'drawnState', value: 'on')
    })

}

def off() {
	log.debug "Executing 'powerOff'"
	// TODO: handle 'powerOff' command
    api('powerOff', [], {
        sendEvent(name: 'drawnState', value: 'off')
    })

}

def pause(){
 api('pause', [], {})
}

// Methods stolen/modified from https://github.com/Dianoga
def api(method, args = [], success = {}) {
def methods = [
'powerOn': [code: "$projectorScreenDrawCode", type: 'get'],
'powerOff': [code: "$projectorScreenRetractCode", type: 'get'],
'pause': [code: "$projectorScreenPauseCode", type: 'get']


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