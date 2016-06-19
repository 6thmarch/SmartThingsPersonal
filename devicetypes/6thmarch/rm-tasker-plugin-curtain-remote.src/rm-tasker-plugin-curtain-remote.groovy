/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Tasker Plugin Curtain Remote 
 *	Version : 1.0.4
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
 *  2016-03-01  V1.0.0  Initial release
 *	2016-03-08	V1.0.1	Switch from HTTP GET request to HTTP POST request
 *	2016-03-31	V1.0.2	Include user authentication
 *	2016-05-14	V1.0.3	Switch on/off state, UI changes
 *	2016-06-20  V1.0.4	Remove colons from MAC ID sent to bridge
 */
 
 preferences {
       section("RM Tasker Plugin HTTP Bridge Configuration"){
       input "server", "text", title: "Server Address",
              description: "This is the domain name or external IP Address of the HTTP Bridge. e.g. mydomain.com or x.x.x.x", defaultValue: '',
              required: true, displayDuringSetup: true

       input "port", "number", title: "Port Number",
              description: "This is the port number of HTTP Bridge.", defaultValue: '',
              required: true, displayDuringSetup: true
              
       input "username", "text", title: "Username",
              description: "This is the username for authentication for HTTP Bridge. (Not available in RM Tasker Plugin yet))", defaultValue: '',
              required: false, displayDuringSetup: true
              
       input "passwd", "password", title: "Password",
              description: "This is the password created for authentication for HTTP Bridge. (Not available in RM Tasker Plugin yet)", defaultValue: '',
              required: false, displayDuringSetup: true
              }
       input "deviceMacId", "text", title: "Device Mac ID",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx",
              required: true, displayDuringSetup: true
                 
               input "curtainOpenCode", "number", title: "Curtain Open Code",
              description: "This is the code to send to HTTP Bridge to open the curtain.",
               required: true, displayDuringSetup: true
              
              input "curtainCloseCode", "number", title: "Curtain Close Code",
              description: "This is the code to send to HTTP Bridge to close the curtain.",
               required: true, displayDuringSetup: true
              
              input "curtainPauseCode", "number", title: "Curtain Pause Code",
              description: "This is the code to send to HTTP Bridge to pause the curtain.",
               required: false, displayDuringSetup: true
	
     }
 
metadata {
	definition (name: "RM Tasker Plugin Curtain Remote", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Momentary"
        capability "Switch"
        capability "Refresh"
        capability "Actuator"
        capability "Button"
	}

	tiles {   
		standardTile("main", "device.switch", width: 3, height: 2) {
	        state "off", label: "Open", action:"switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
            state "on", label: "Closed", action:"switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
        }
        
        standardTile("open", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "open",  icon:"st.switches.switch.off", action:"off", backgroundColor: "#ffffff"
        }
        
        standardTile("close", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "close",  icon:"st.switches.switch.on", action:"on", backgroundColor: "#ffffff"
        }
        
        standardTile("pause", "device.button", inactiveLabel:false, decoration:"flat") {
            state "default", label: "pause",  icon:"st.switches.switch.off", action:"pause", backgroundColor: "#ffffff"
        }  
      
        
		main "main"
		details(["main", "open", "pause", "close"])
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
    api('close', [], {
        sendEvent(name: 'switch', value: 'on')
    })
}

def off() {
    api('open', [], {
        sendEvent(name: 'switch', value: 'off')
    })
}

def pause(){
 api('pause', [], {})
}


def api(method, args = [], success = {}) {

def methods = [
'open': [code: curtainOpenCode, type: 'post'],
'close': [code: curtainCloseCode, type: 'post'],
'pause': [code: curtainPauseCode, type: 'post']
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

