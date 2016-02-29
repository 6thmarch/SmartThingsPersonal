/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Bridge Virtual Switch 
 *	Version : 1.0.1
 * 
 * 	Description:
 * 		RM Bridge Virtual Switch is a SmartThings Device Type that allows you to turn on or off devices 
 * 		by sending Infrared or RF signal from Broadlink RM device to control your home devices.
 * 		This device requires Android RM Bridge to be installed and started in an android device within the 
 * 		same wi-fi network as the Broadlink RM device.
 * 
 * 	Requirements:
 * 		An android device (Android Box/Tablet/Phone) within the same wi-fi network as the Broadlink RM device, with Android RM Bridge installed and running.
 * 		Broadlink RM device
 * 		SmartThings Hub
 * 		Amazon Echo (Only for voice control)
 * 
 * 	References:
 * 		Android RM Bridge created by Jochen Ruehl which you can get from http://rm-bridge.fun2code.de
 * 		Broadlink RM Device: http://www.broadlink.com.cn/en/home-en.html
 * 
 * 	Instructions:
 * 		1. Ensure you have an Android device and Broadlink RM device.
 * 		2. Make sure your Android device and Broadlink RM device is within the same wi-fi network.
 * 		3. Install Android RM Bridge on your Android device. You can get the app from the references above.
 * 		4. After installation, open the app, go to the app settings, enable authentication and set up username and password, and then start the server.
 * 		5. From the Android RM Bridge reference site, click "Manage Codes" to enter the Android RM Bridge Management Console.
 * 		6. 	Step 1: Enter the IP Address and Port Number stated in the Android RM Bridge App. Click "Load Devices". Enter 
 * 				the username and password created in the app.
 * 		7. 	Step 2: Select the Broadlink RM device you want the signal to be sent from.
 * 		8. 	Step 3: Enter a code for that Infrared / RF signal from your remote.
 * 		9.	Step 4: Click "Learn" and press the button on your remote pointing at the Broadlink RM device.
 * 		10.	Step 5: Click "Test Code" button to ensure that it recorded your signal correctly.
 * 		11. 	Step 6: Return to the Main page of the Management Console and click "Manage Code Shortcuts"
 * 		12. 	Step 7: Repeat Step 1.
 * 		13. 	Step 8: Make sure the code you created is listed.
 * 		14. Add Broadlink™ RM Virtual Switch device type in SmartThings App/SmartThings Developer API Website.
 * 		15. In the device Preferences, specify:
 * 			a. Name of switch
 * 			b. Android Device domain name or External IP Address (Note: Internal IP Address does not work here) 
 * 			   If your external IP Address is always changing and you do not have a domain name, you may consider 
 * 			   using Dynamic DNS Service like no-ip.com .	   
 * 			   e.g. mydomain.com or x.x.x.x * 				
 * 			c. Port Number listed in the Android RM Bridge App 
 * 			   (Note: Make sure to set-up Port Forwarding in your router to forward that port on TCP Protocol to the IP Address 
 * 			   stated in the Android RM Bridge App.)
 * 			d. Username created for Android RM Bridge App
 * 			e. Password created for Android RM Bridge App
 * 			f. ON Code - Code created in the Android RM Bridge Management Console. 
 * 				     This code will be sent when switch is turned on.
 * 			g. OFF Code - Code created in the Android RM Bridge Management Console.
 * 				     This code will be sent when the switch is turned off.
 * 		16. That's it! You can now control your Broadlink RM device to send Infrared or RF Signals through SmartThings App.
 * 		
 * 		17. To also control this device using Amazon Echo via Voice:
 * 			a. Go to Amazon Echo SmartApps in SmartThings App and add the device into the list.
 * 			b. Tell Alexa/Amazon (Amazon Echo) to "Discover New Devices".
 * 		18. You are done! You can now also control your Broadlink RM device to send Infrared or RF Signals through voice using
 * 		    Amazon Echo. Just say "Alexa, turn on/off [switch name]".
 * 
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
 *  2015-12-09  V1.0.0  Initial release
 *  2015-12-12  V1.0.1  Code clean up and bug fix
 *	2016-02-29	V1.0.1	Renamed device type
 */

metadata {
	
    definition (
    name: "RM Bridge Virtual Switch",
    description: "Control (On/Off) devices through infrared or RF using BroadLink™ RM devices. Android RM Bridge is required to be installed and started in an Android device to bridge the connection with the BroadLink™ RM device. This switch sends the code to Android RM Bridge which in turn trigger the sending of IR/RF signal from the BroadLink™ RM device. ",
    namespace: "6thmarch",
    category: "Convenience",
    author: "Benjamin Yam") {
		capability "Switch"
        capability "Momentary"
	}
    
    preferences {
       section("RM Bridge Server Configuration"){
       input "server", "text", title: "Server Address",
              description: "This is the domain name or external IP Address of the Android RM Bridge Server. e.g. mydomain.com or x.x.x.x", defaultValue: '',
              required: true, displayDuringSetup: true

       input "port", "text", title: "Port Number",
              description: "This is the port number of Android RM Bridge Server.", defaultValue: '',
              required: true, displayDuringSetup: true
              
       input "username", "text", title: "Username",
              description: "This is the username for authentication for Android RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              
       input "passwd", "password", title: "Password",
              description: "This is the password created for authentication for Android RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              }
       input "onCode", "text", title: "ON Code",
              description: "This is the code to send to Android RM Bridge if the switch is turned on. e.g. FanOn",
              required: true, displayDuringSetup: true
       input "offCode", "text", title: "OFF Code",
              description: "This is the code to send to Android RM Bridge if the switch is turned off. e.g. FanOff",
              required: true, displayDuringSetup: true
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
    makeJSONBroadlinkRMBridgeRequest("$onCode")
}

def off() {
	sendEvent(name: "switch", value: "off")
    makeJSONBroadlinkRMBridgeRequest("$offCode")
}

//Send code to RM Bridge Server to trigger sending of IR/RF signal from Broadlink RM device.
def makeJSONBroadlinkRMBridgeRequest(String code) {
    log.debug "Sending code: $code"
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
