//
//  SocketIOManager.swift
//  SocketIO-Chat
//
//  Created by 이동영 on 07/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//


import UIKit
import SocketIO

class SocketIOManager: NSObject {
    
    
    var manager :SocketManager?
    // var socket:SocketIOClient = manager.defaultSocket
    
    override init() {
        super.init()
        
        let delegate = UIApplication.shared.delegate as! AppDelegate
        self.manager = SocketManager(socketURL: URL(string: "\(delegate.serverIp)")!, config: [.log(true), .compress])
        
        
        //self.addHandler()
        
        //self.addHandlers()
    }
    
    /**=============================
     - Note: 소켓 접속 시도 메소드
     ==============================*/
    func establishConnection() {
        //  self.socket.connect()
        print("connecting")
    }
    
    
    /**=============================
     - Note: 소켓 접속 헤제 메소드
     ==============================*/
    func closeConnection() {
        //  self.socket.disconnect()
    }
    
    func addHandler(){
        /*
         
         self.socket.on(clientEvent: .connect) {data, ack in
         print("socket connected")
         }
         
         
         self.socket.on("fromServer") {data, ack in
         print("이벤트 헨들링 - \(data[0])")
         self.socket.emit("fromIOS",["data":"\(Date()) - IOS 클라이언트"])
         }
         
         */
    }
    
    
    
    
    
}
