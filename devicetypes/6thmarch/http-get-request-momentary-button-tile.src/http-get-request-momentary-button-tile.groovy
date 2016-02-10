/**
 *  Copyright 2015 Benjamin Yam
 *	
 *	HTTP GET Request Momentary Button Tile
 *	Version : 1.0.0
 * 
 * 	Description:
 * 		HTTP GET Request Momentary Button Tile allows you to send a GET request on pushing the button.
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
 *  2016-01-08  V1.0.0  Initial release
 */

metadata {
	
    definition (
    name: "HTTP GET Request Momentary Button Tile",
    description: "Sends a GET Request on pushing the button",
    namespace: "6thmarch",
    category: "Convenience",
    author: "Benjamin Yam") {
		capability "Switch"
        capability "Actuator"
        capability "Momentary"
	}
    
    preferences {
       section("HTTP GET Request Momentary Button Tile Configuration"){
       input "url", "text", title: "URL",
              description: "This is the URL (up to '&message=') to send the message to. E.g http://domain.com/sendmessage?key=abcdefg&message=",
              required: true, displayDuringSetup: true
       input "message", "text", title: "Message",
              description: "This is the message to send when the button is pushed.",
              required: true, displayDuringSetup: true
   	 }
     }

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Push', action: "momentary.push", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'Push', action: "momentary.push", backgroundColor: "#53a7c0"
		}
        main "switch"
		details(["switch"])
	}
}

def push() {
	sendEvent(name: "switch", value: "on", isStateChange: true, display: false)
	sendEvent(name: "switch", value: "off", isStateChange: true, display: false)
	sendEvent(name: "momentary", value: "pushed", isStateChange: true)
    sendGetRequest(message) 

}

def on() {
	push()
}

def off() {
	push()
}

//Send GET Request
def sendGetRequest(String msg) {
    log.debug "Sending GET Request to url: $url$msg"
    log.debug "$url$msg"
    def params = [
        uri:  "$url$msg",
        contentType: 'application/json'        
    ]
    try {
        httpGet(params) {resp ->
            log.debug "resp data: ${resp.data}"
        }
    } catch (e) {
        log.error "error: $e"

    }
}
