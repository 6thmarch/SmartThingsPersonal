/**
 *  Copyright 2015 Benjamin Yam
 *	
 *	HTTP GET Request Switch 
 *	Version : 1.0.0
 * 
 * 	Description:
 * 		HTTP GET Request Switch allows you to send a GET request on turning on/off the switch.
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
 *  2016-01-02  V1.0.0  Initial release
 */

metadata {
	
    definition (
    name: "HTTP GET Request Switch",
    description: "Sends a GET Request on turning on/off this switch",
    namespace: "6thmarch",
    category: "Convenience",
    author: "Benjamin Yam") {
		capability "Switch"
        capability "Momentary"
	}
    
    preferences {
       section("HTTP GET Request Switch Configuration"){      
       input "onRequest", "text", title: "ON URL",
              description: "This is the URL to send the GET request when the switch is turned on.",
              required: true, displayDuringSetup: true
       input "offRequest", "text", title: "OFF URL",
              description: "This is the URL to send the GET request when the switch is turned off.",
              required: true, displayDuringSetup: true
   	 }
     }

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: '${currentValue}', action: "on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
			state "on", label: '${currentValue}', action: "off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
		}
		standardTile("on", "device.momentary", decoration: "flat") {
			state "default", label: 'On', action: "on"
		}
		standardTile("off", "device.momentary", decoration: "flat") {
			state "default", label: 'Off', action: "off"
		}
        main "switch"
		details(["switch","on","off"])
	}
}


def on() {
	sendEvent(name: "switch", value: "on")
    sendGetRequest("$onRequest")
}

def off() {
	sendEvent(name: "switch", value: "off")
    sendGetRequest("$offRequest")
}

//Send GET Request
def sendGetRequest(String url) {
    log.debug "Sending GET Request to url: $url"
    def params = [
        uri:  "$url",
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
