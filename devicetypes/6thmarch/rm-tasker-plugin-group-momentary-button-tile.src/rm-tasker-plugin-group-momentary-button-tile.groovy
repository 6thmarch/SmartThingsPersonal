/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Tasker Plugin Group Momentary Button Tile
 *	Version : 1.0.2
 * 
 * 	Description:
 * 		RM Tasker Plugin Group Momentary Button Tile is a SmartThings Device Type that allows you to turn on or off devices 
 * 		by sending Infrared or RF signal from Broadlink RM device to control your home devices.
 * 		This device requires RM Tasker Plugin to be installed and started in an android device within the 
 * 		same wi-fi network as the Broadlink RM device.
 * 
 * 	Requirements:
 * 		An android device (Android Box/Tablet/Phone) within the same wi-fi network as the Broadlink RM device, with RM Tasker Plugin installed and running.
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
 *	2016-03-08	V1.0.1	Switch HTTP GET request to HTTP POST request
 *	2016-03-23	V1.0.2	Bug fix
 */
 
  import groovy.transform.Field
 @Field final int MAX_CODES_PER_GROUP = 10 //on() and off() need to be changed if this value is edited.
 
metadata {
	definition (name: "RM Tasker Plugin Group Momentary Button Tile", 
    description: "Control devices through infrared or RF using BroadLink™ RM devices. RM Tasker Plugin is required to be installed and started in an Android device to bridge the connection with the BroadLink™ RM device. This switch sends the code to HTTP Bridge which in turn trigger the sending of IR/RF signal from the BroadLink™ RM device. ",
    namespace: "6thmarch", 
    category: "Convenience",
    author: "Benjamin Yam") {
		capability "Actuator"
		capability "Switch"
		capability "Momentary"
		capability "Sensor"
	}

	// simulator metadata
	simulator {
	}


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
             1.upto(MAX_CODES_PER_GROUP, {
       		 input "code${it}", "number", title: "Code ${it}",
              description: "This is the code to send to HTTP Bridge if the switch is turned on.",
              required: false, displayDuringSetup: true
                       
              input "deviceMacId${it}", "text", title: "Device Mac ID ${it}",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx", defaultValue: '00:00:00:00:00:00',
              required: true, displayDuringSetup: true
              
      		 input "repeatVal${it}", "number", title: "Repeat Value ${it}",
              description: "This is the number of times to send the command.", defaultValue: '1',
              required: true, displayDuringSetup: true
              
			})
    }
    
    
	// UI tile definitions
	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Push', action: "momentary.push", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'Push', action: "momentary.push", backgroundColor: "#53a7c0"
		}
		main "switch"
		details "switch"
	}
}

def parse(String description) {
}

def push() {
	sendEvent(name: "switch", value: "on", isStateChange: true, display: false)
	sendEvent(name: "switch", value: "off", isStateChange: true, display: false)
	sendEvent(name: "momentary", value: "pushed", isStateChange: true)
    
    if("${code1}" != "null" && "${deviceMacId1}" != "null"){
        	api('code1', ['deviceMacId': deviceMacId1, 'repeat' : repeatVal1], {})
    }
    if("${code2}" != "null" && "${deviceMacId2}" != "null"){
        	api('code2', ['deviceMacId': deviceMacId2, 'repeat' : repeatVal2], {})
    }
    if("${code3}" != "null" && "${deviceMacId3}" != "null"){
        	api('code3', ['deviceMacId': deviceMacId3, 'repeat' : repeatVal3], {})

    }
    if("${code4}" != "null" && "${deviceMacId4}" != "null"){
        	api('code4', ['deviceMacId': deviceMacId4, 'repeat' : repeatVal4], {})

    }
    if("${code5}" != "null" && "${deviceMacId5}" != "null"){
        	api('code5', ['deviceMacId': deviceMacId5, 'repeat' : repeatVal5], {})

    }
    if("${code6}" != "null" && "${deviceMacId6}" != "null"){
        	api('code6', ['deviceMacId': deviceMacId6, 'repeat' : repeatVal6], {})

    }
    if("${code7}" != "null" && "${deviceMacId7}" != "null"){
        	api('code7', ['deviceMacId': deviceMacId7, 'repeat' : repeatVal7], {})

    }
    if("${code8}" != "null" && "${deviceMacId8}" != "null"){
        	api('code8', ['deviceMacId': deviceMacId8, 'repeat' : repeatVal8], {})

    }
    if("${code9}" != "null" && "${deviceMacId9}" != "null"){
        	api('code9', ['deviceMacId': deviceMacId9, 'repeat' : repeatVal9], {})

    }
    if("${code10}" != "null" && "${deviceMacId10}" != "null"){
        	api('code10', ['deviceMacId': deviceMacId10, 'repeat' : repeatVal10], {})

    }


}

def on() {
	push()
}

def off() {
	push()
}


def api(method, args = [], success = {}) {

def methods = [
'code1': [code: code1, type: 'post'],
'code2': [code: code2, type: 'post'],
'code3': [code: code3, type: 'post'],
'code4': [code: code4, type: 'post'],
'code5': [code: code5, type: 'post'],
'code6': [code: code6, type: 'post'],
'code7': [code: code7, type: 'post'],
'code8': [code: code8, type: 'post'],
'code9': [code: code9, type: 'post'],
'code10': [code: code10, type: 'post']
    ]
def request = methods.getAt(method)
    doRequest(request.code,  args, request.type, success)
}
def doRequest(code, args, type, success) {
    log.debug "Calling $type : $code : $args"
    def repeatVal = 1
    if(args['repeat']){
    	repeatVal = args['repeat']
    }
    def deviceMacId
    if(args['deviceMacId'])
    {
    	deviceMacId = args['deviceMacId']
    }
    else
    {
    log.debug "Error: Device Mac ID not supplied"
    }
   // log.debug "repeatVal: $repeatVal"
def params = [
uri: "http://$server:$port",
path: "/send",
headers: [
'Accept': "application/json"
        ],
query: ['deviceMac' : deviceMacId, 'codeId' : code, 'repeat': repeatVal] //args 
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

