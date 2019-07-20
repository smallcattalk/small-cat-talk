//
//  NetworkProcesser.swift
//  SocketChat
//
//  Created by 이동영 on 29/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Alamofire

class NetworkProcesser: NSObject {

    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    
    ///================================
    ///         유저 업데이트
    ///================================
    func currentUserUpdate(){
        
        let currentUserId = self.delegate.currentUser!._id
        
        let params : Parameters = [
            "_id":currentUserId!
        ]
        
        
        let updateUserRequest = Alamofire.request("\(self.delegate.serverIp)/update/user", method: .post , parameters: params, encoding: JSONEncoding.default)
        
     
        updateUserRequest.responseJSON{
            
            res in
            
            let responseJson = res.result.value as! NSDictionary
            
            let resultCode = responseJson["result"] as! Int
            
            switch(resultCode){
            
            case -1 :
                () // BAD REQUEST
            case 0:
                () // WRONG ID
            case 1:
                
                let userJson = responseJson["user"] as! NSDictionary
                
                let user = UserVO()
                
                user._id = (userJson["_id"] as! String)
                
                user.userName = (userJson["userName"] as! String)
                
                user.profileImgUrl = userJson["profileImgUrl"] as! String
                
                user.userId = (userJson["userId"] as! String)
                
                user.comment = (userJson["comment"] as! String)
                
                user.roomsList = (userJson["roomsList"] as! NSArray)
                
                user.friendsList = (userJson["friendsList"] as! NSArray)
                
                 user.profileImg = UIImage(data: try! Data(contentsOf: URL(string: "\(self.delegate.serverIp)/\((userJson["profileImgUrl"] as! String))")!))
                user.onChange = true
                self.delegate.currentUser = nil
                
                self.delegate.currentUser = user
                
            default:
                ()
                
                
            }
            
            
            
            
        }
        
        
        
    }
    
    func loadRoom (_id :String ,VC:UIViewController){
        

        var roomsList = [RoomVO]()
        
        let params : Parameters = [
            "_id":_id
        ]
        
        let roomListReq = Alamofire.request("\(self.delegate.serverIp)/room/list", method: .post, parameters: params, encoding: JSONEncoding.default)
        
        
        roomListReq.responseJSON{
            
            res in
            
            let alartMaker = AlertMaker()
            
            var rooms = [RoomVO]()
            
           guard let responseJson = res.result.value as? NSDictionary else{
                   // alartMaker.makeAlert(title: "서버 오류", message: "서버가 동작하지 않습니다.", VC: VC)
                    return
            }
            
            let resultCode = responseJson["result"] as! Int

            if(resultCode == 0 ){
                alartMaker.makeAlert(title: "불러오기 실패", message: "참여중인 대화방이 없습니다.", VC: VC)
            }
            else{
                let roomsListJson = responseJson["roomsList"] as! NSArray
                let usersListsJson = responseJson["usersLists"] as! NSArray
    
                for i in 0..<roomsListJson.count {
                
                    var room = RoomVO()
                    room.usersList = NSMutableArray()
                    room._id = ((roomsListJson[i] as! NSDictionary)["_id"] as! String)
                    room.unreadCount = ((roomsListJson[i] as! NSDictionary)["unreadCount"] as! Int)
                    
                    let usersList2 = (usersListsJson[i] as! NSArray)
                    
                    for user in usersList2 {
                        let userDic = user as! NSDictionary
                        let userVo = UserVO()
                        userVo._id = userDic["_id"] as! String
                        userVo.comment = userDic["comment"] as! String
                        userVo.userId = userDic["userId"] as! String
                        userVo.userName = userDic["userName"] as! String
                        userVo.profileImgUrl = userDic["profileImgUrl"] as! String
                        userVo.profileImg = UIImage.init(data: try! Data.init(contentsOf: URL.init(string: "\(self.delegate.serverIp)/\(userVo.profileImgUrl!)")!))
                        room.usersList?.add(userVo)
                    }
                    
                  
                    if((room.usersList?.count) == 2){
                        for user in room.usersList!{
                            if((user as! UserVO)._id != self.delegate.currentUser?._id){
                                room.friend = user as? UserVO}}
                        
                    }
                    rooms.append(room)
                }
                guard let RoomListVC = VC as? RoomListVC else{
                    return
                }
                print("방목록 업데이트")
                
                RoomListVC.roomsList = rooms
                
            }
        }
    }
    
    func addGroupRoom(usersList:[String],VC:UIViewController){
        
        let params :Parameters = [
            "usersList":usersList
        ]
        let newGroupRoom  = makeRequest(params: params, url: "/groupRoom")

        newGroupRoom.responseJSON{
            res in
            print(res)
        }
        
    }
    
    
    func makeRequest(params:Parameters,url:String)->(DataRequest){
        
               let request = Alamofire.request("\(self.delegate.serverIp)\(url)", method: .post, parameters: params, encoding: JSONEncoding.default)
        
        return request
    }
    
    
    
    
}
