/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	AutoRemote Switch 
 *	Version : 1.0.2
 * 
 * 	Description:
 * 		AutoRemote Switch allows you to send a message to AutoRemote when turning on/off the switch.
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
 *	2016-02-13  V1.0.1	Renamed to AutoRemote GET Request Switch, included support for password and multiple device, split url into components (url/key)
 *	2016-03-08	V1.0.2	Renamed to AutoRemote Switch, edited description, switch from HTTP GET request to HTTP POST Request
 */
import groovy.transform.Field
 @Field final int MAX_NUM_OF_DEVICES = 10 //key need to be changed if this value is edited.


metadata {
	
    definition (
    name: "AutoRemote Switch",
    description: "Sends a message to AutoRemote when turning on/off this switch",
    namespace: "6thmarch",
    category: "Convenience",
    author: "Benjamin Yam") {
		capability "Switch"
        capability "Momentary"
	}
    
    preferences {
       section("AutoRemote Switch Configuration"){
       input "url", "text", title: "URL",
              description: "This is the URL to send the message to. (Optional, https://autoremotejoaomgcd.appspot.com/sendmessage by default)" ,
              required: false, displayDuringSetup: true, defaultValue: "https://autoremotejoaomgcd.appspot.com/sendmessage"
       input "onMessage", "text", title: "ON Message",
              description: "This is the message to send when the switch is turned on.",
              required: true, displayDuringSetup: true
       input "offMessage", "text", title: "OFF Message",
              description: "This is the message to send when the switch is turned off.",
              required: true, displayDuringSetup: true
              
		input "passwd", "password", title: "Password",
              description: "This is the password for AutoRemote (Optional)", defaultValue: '',
              required: false, displayDuringSetup: true
              
		1.upto(MAX_NUM_OF_DEVICES, {       		 
    		 input "key${it}", "text", title: "Key ${it}",
              description: "This is the key of the device to send the message to. It can be found on your AutoRemote URL.",
              required: false, displayDuringSetup: true
   		})
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
    scanDevicesToSendRequest("on")
}

def off() {
	sendEvent(name: "switch", value: "off")
    scanDevicesToSendRequest("off")
}

def scanDevicesToSendRequest(String method){
	if("${key1}" != "null"){
       	api(method, ['key' : key1, 'password' : passwd], {})

    }
    if("${key2}" != "null"){
       	api(method, ['key' : key2, 'password' : passwd], {})

    }
    if("${key3}" != "null"){
       	api(method, ['key' : key3, 'password' : passwd], {})

    }
    if("${key4}" != "null"){
       	api(method, ['key' : key4, 'password' : passwd], {})

    }
    if("${key5}" != "null"){
       	api(method, ['key' : key5, 'password' : passwd], {})

    }
    if("${key6}" != "null"){
       	api(method, ['key' : key6, 'password' : passwd], {})

    }
    if("${key7}" != "null"){
       	api(method, ['key' : key7, 'password' : passwd], {})

    }
    if("${key8}" != "null"){
       	api(method, ['key' : key8, 'password' : passwd], {})

    }
    if("${key9}" != "null"){
       	api(method, ['key' : key9, 'password' : passwd], {})

    }
    if("${key10}" != "null"){
       	api(method, ['key' : key10, 'password' : passwd], {})

    }

}


def api(method, args = [], success = {}) {

def methods = [
'on': [msg: onMessage, type: 'post'],
'off': [msg: offMessage, type: 'post']
    ]
def request = methods.getAt(method)
    doRequest(request.msg, args, request.type, success)
}
def doRequest(msg, args, type, success) {
    log.debug "Calling $type : $msg : $args"
    def key
    def passwd
    if (args['key'])
    {
    	key = args['key']
    }
    else
    {
    	log.debug "Key not specified"
    }
    if(args['password'])
    {
    	passwd = args['password']
    }
    else
    {
    	log.debug "Password not specified"
    }

def params = [
uri: "$url",
headers: [
'Accept': "application/json"
        ],
query: ['key' : key, 'message' : msg, 'password': passwd] 
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
