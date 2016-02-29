/**
 *  RM Bridge HDMI Switcher Remote
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
              
       input "HDMI1Code", "text", title: "HDMI Input 1 Code",
              description: "This is the code to send RM Bridge to activate HDMI Input 1.", defaultValue: '',
              required: false, displayDuringSetup: true

       input "HDMI2Code", "text", title: "HDMI Input 2 Code",
              description: "This is the code to send RM Bridge to activate HDMI Input 2.", defaultValue: '',
              required: false, displayDuringSetup: true

       input "HDMI3Code", "text", title: "HDMI Input 3 Code",
              description: "This is the code to send RM Bridge to activate HDMI Input 3.", defaultValue: '',
              required: false, displayDuringSetup: true

       input "HDMI4Code", "text", title: "HDMI Input 4 Code",
              description: "This is the code to send RM Bridge to activate HDMI Input 4.", defaultValue: '',
              required: false, displayDuringSetup: true
       input "HDMI5Code", "text", title: "HDMI Input 5 Code",
              description: "This is the code to send RM Bridge to activate HDMI Input 5.", defaultValue: '',
              required: false, displayDuringSetup: true
     }
 
metadata {
	definition (name: "RM Bridge HDMI Switcher Remote", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Music Player"
        capability "Momentary"
        capability "Refresh"
        capability "Actuator"
        capability "Button"
	}

	tiles {
		standardTile("hdmiInput1", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "default", label: "HDMI Input 1", action:"hdmiInput1", icon: "st.Electronics.electronics6.png", backgroundColor: "#ffffff"           
                }
        
                standardTile("hdmiInput2", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "default", label: "HDMI Input 2", action:"hdmiInput2", icon: "st.Electronics.electronics6.png", backgroundColor: "#ffffff"           
                }
                standardTile("hdmiInput3", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "default", label: "HDMI Input 3", action:"hdmiInput3", icon: "st.Electronics.electronics6.png", backgroundColor: "#ffffff"           
                }
                standardTile("hdmiInput4", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "default", label: "HDMI Input 4", action:"hdmiInput4", icon: "st.Electronics.electronics6.png", backgroundColor: "#ffffff"           
                }
                standardTile("hdmiInput5", "device.button", inactiveLabel: false, decoration: "flat", canChangeBackground: true, canChangeIcon: true) {
	        state "default", label: "HDMI Input 5", action:"hdmiInput5", icon: "st.Electronics.electronics6.png", backgroundColor: "#ffffff"           
                }
        
        
	main "hdmiInput2"
	details(["hdmiInput1","hdmiInput2","hdmiInput3","hdmiInput4","hdmiInput5"])
	}
    
    command "hdmiInput1"
    command "hdmiInput2"
    command "hdmiInput3"
    command "hdmiInput4"
    command "hdmiInput5"
    
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



// handle commands

def hdmiInput1(){
	api('hdmiInput1', [], {})
}

def hdmiInput2(){
	api('hdmiInput2', [], {})
}

def hdmiInput3(){
	api('hdmiInput3', [], {})
}

def hdmiInput4(){
	api('hdmiInput4', [], {})
}

def hdmiInput5(){
	api('hdmiInput5', [], {})
}


// Methods stolen/modified from https://github.com/Dianoga
def api(method, args = [], success = {}) {
def methods = [
'hdmiInput1': [code: "$HDMI1Code", type: 'get'],
'hdmiInput2': [code: "$HDMI2Code", type: 'get'],
'hdmiInput3': [code: "$HDMI3Code", type: 'get'],
'hdmiInput4': [code: "$HDMI4Code", type: 'get'],
'hdmiInput5': [code: "$HDMI5Code", type: 'get']
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