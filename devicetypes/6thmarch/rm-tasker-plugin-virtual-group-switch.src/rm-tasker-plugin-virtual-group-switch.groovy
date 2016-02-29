/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Tasker Plugin Virtual Group Switch 
 *	Version : 1.0.0
 * 
 * 	Description:
 * 		RM Tasker Plugin Virtual Group Switch is a SmartThings Device Type that allows you to turn on or off devices 
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
 */
 import groovy.transform.Field
 @Field final int MAX_CODES_PER_GROUP = 10 //on() and off() need to be changed if this value is edited.


metadata {
	
    definition (
    name: "RM Tasker Plugin Virtual Group Switch ",
    description: "Control (On/Off) devices through infrared or RF using BroadLink™ RM devices. RM Tasker Plugin is required to be installed and started in an Android device to bridge the connection with the BroadLink™ RM device. This switch sends the code to the HTTP Bridge which in turn trigger the sending of IR/RF signal from the BroadLink™ RM device. ",
    namespace: "6thmarch",
    category: "Convenience",
    author: "Benjamin Yam") {
		capability "Switch"
        capability "Momentary"
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
       		 input "onCode${it}", "number", title: "ON Code ${it}",
              description: "This is the code to send to HTTP Bridge if the switch is turned on.",
              required: false, displayDuringSetup: true
                       
              input "onDeviceMacId${it}", "text", title: "ON Device Mac ID ${it}",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx",
              required: true, displayDuringSetup: true
              
      		 input "onRepeatVal${it}", "number", title: "ON Repeat Value ${it}",
              description: "This is the number of times to send the command.", defaultValue: '1',
              required: true, displayDuringSetup: true
              
               input "offCode${it}", "number", title: "OFF Code ${it}",
              description: "This is the code to send to HTTP Bridge if the switch is turned off.",
              required: false, displayDuringSetup: true
              
              input "offDeviceMacId${it}", "text", title: "OFF Device Mac ID ${it}",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx",
              required: true, displayDuringSetup: true
              
      		 input "offRepeatVal${it}", "number", title: "OFF Repeat Value ${it}",
              description: "This is the number of times to send the command.", defaultValue: '1',
              required: true, displayDuringSetup: true
			})
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

            
    if("${onCode1}" != "null" && "${onDeviceMacId1}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId1, onCode1, onRepeatVal1)
    }
     if("${onCode2}" != "null" && "${onDeviceMacId2}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId2, onCode2, onRepeatVal2)
    }
     if("${onCode3}" != "null" && "${onDeviceMacId3}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId3, onCode3, onRepeatVal3)
    }
     if("${onCode4}" != "null" && "${onDeviceMacId4}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId4, onCode4, onRepeatVal4)
    }
     if("${onCode5}" != "null" && "${onDeviceMacId5}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId5, onCode5, onRepeatVal5)
    }
     if("${onCode6}" != "null" && "${onDeviceMacId6}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId6, onCode6, onRepeatVal6)
    }
     if("${onCode7}" != "null" && "${onDeviceMacId7}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId7, onCode7, onRepeatVal7)
    }
     if("${onCode8}" != "null" && "${onDeviceMacId8}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId8, onCode8, onRepeatVal8)
    }
     if("${onCode9}" != "null" && "${onDeviceMacId9}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId9, onCode9, onRepeatVal9)
    }
     if("${onCode10}" != "null" && "${onDeviceMacId10}" != "null"){
         makeJSONBroadlinkRMBridgeRequest(onDeviceMacId10, onCode10, onRepeatVal10)
    }
    
    
}

def off() {
	sendEvent(name: "switch", value: "off")
    
    if("${offCode1}" != "null" && "${offDeviceMacId1}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId1, offCode1, offRepeatVal1)
    }
        if("${offCode2}" != "null" && "${offDeviceMacId2}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId2, offCode2, offRepeatVal2)
    }
        if("${offCode3}" != "null" && "${offDeviceMacId3}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId3, offCode3, offRepeatVal3)
    }
        if("${offCode4}" != "null" && "${offDeviceMacId4}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId4, offCode4, offRepeatVal4)
    }
        if("${offCode5}" != "null" && "${offDeviceMacId5}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId5, offCode5, offRepeatVal5)
    }
        if("${offCode6}" != "null" && "${offDeviceMacId6}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId6, offCode6, offRepeatVal6)
    }
        if("${offCode7}" != "null" && "${offDeviceMacId7}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId7, offCode7, offRepeatVal7)
    }
        if("${offCode8}" != "null" && "${offDeviceMacId8}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId8, offCode8, offRepeatVal8)
    }
        if("${offCode9}" != "null" && "${offDeviceMacId9}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId9, offCode9, offRepeatVal9)
    }
        if("${offCode10}" != "null" && "${offDeviceMacId10}" != "null" ){
         makeJSONBroadlinkRMBridgeRequest(offDeviceMacId10, offCode10, offRepeatVal10)
    }

}

//Send code to RM Bridge Server to trigger sending of IR/RF signal from Broadlink RM device.
def makeJSONBroadlinkRMBridgeRequest(String deviceMacId, int code, int repeatVal) {
    log.debug "Sending code: ${code}"
    log.debug "http://$server:$port/send?deviceMac=$deviceMacId&codeId=$code&repeat=$repeatVal"
    def params = [
        //uri:  "http://$username:$passwd@$server:$port/code/",
        //path: "$code",
        uri: "http://$server:$port/send?deviceMac=$deviceMacId&codeId=$code&repeat=$repeatVal",
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

