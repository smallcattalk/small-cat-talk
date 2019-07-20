//
//  ChatRoomVC.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Alamofire
import SocketIO

class ChatRoomVC: UIViewController,UITextFieldDelegate {
    
    
    var keyboardControll : KeyboardControl?
    
    let alertMaker = AlertMaker()
    
    var currentRoomId  : String?
    
    var currentRoom : RoomVO?
    
    var friendId : String?
    
    @IBAction func btnCamera(_ sender: Any) {
        self.imgPicker(.photoLibrary)
    }
    
    var chatsList: [ChatVO] = []
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    var socketManager : SocketManager?
    
    var socket : SocketIOClient?
    
    
    @IBOutlet var tbvChats: UITableView!
    // var currentUser : String?
    
    var textFields = [UITextField]()
    
    @IBOutlet var bottomView: UIView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.chatLoader(roomId: self.currentRoomId!)
        
        self.tbvChats.delegate = self
        self.tbvChats.dataSource = self
        
        self.socketManager = self.delegate.socketManager
        
   //     self.tbvChats.layoutIfNeeded()
  //      self.tbvChats.setContentOffset(CGPoint(x: 0, y: self.tbvChats.contentSize.height - self.tbvChats.frame.height + 10), animated: false)
        self.txfMessage.delegate  = self
        self.textFields.append(self.txfMessage)
        self.tbvChats.estimatedRowHeight = 90
        
        /************************************
         *       테이블뷰 전체목록 생성시점에
         *           콘텐츠레이아웃에
         *           따른 높이 재설정
         ***********************************/
        self.tbvChats.rowHeight = UITableView.automaticDimension
        
        self.keyboardControll = KeyboardControl(self.textFields, view: self.view, height: 550,duration:1)
        
        //self.alertMaker.makeAlert(title: "방 ID", message: self.currentRoomId!, VC: self)
        
        if(self.currentRoomId == ""){
            self.loadRoom(roomId: self.currentRoomId!)
            
            self.firstEntrance()
            
        }else{
            
            self.loadRoom(roomId: self.currentRoomId!)
            
        }
        
        // Do any additional setup after/Users/ldcpaul/Downloads/Project/작은고양이톡/SocketChat/SocketChat/VC loading the view.
        
    }
    override func viewWillAppear(_ animated: Bool) {
        
        self.btnSend.clipsToBounds = true
        self.btnSend.layer.cornerRadius = 5
        self.connectSocket()
        self.addHandler()
        self.tabBarController?.tabBar.isHidden = true
        
    }
    
    
    @IBAction func blurKeyboard(_ sender: Any) {
        
        if(self.txfMessage.isFirstResponder){
            self.txfMessage.resignFirstResponder()
        }
        
    }
    override func viewWillDisappear(_ animated: Bool) {
        self.tabBarController?.tabBar.isHidden = false
        print("socket end")
       self.socketManager?.disconnectSocket(self.socket!)
       self.socketManager?.removeSocket(self.socket!)
        
    }
    
    @IBOutlet var bgView: UIView!
    
    @IBOutlet var txfMessage: UITextField!
    
    @IBOutlet var btnSend: UIButton!
    
    @IBAction func btnSend(_ sender: Any) {
        
        self.newChat(userId: self.delegate.currentUser!._id!, roomId: self.currentRoomId!, chat: self.txfMessage.text!)
        self.txfMessage.text = ""
        self.keyboardControll?.blurKeyBoard(self)
        
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
    }
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
}

// - MARK: - 테이블 델리게이트
extension ChatRoomVC : UITableViewDelegate,UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.chatsList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if( (self.chatsList[indexPath.row].user!._id! == self.delegate.currentUser!._id!)){
            if(self.chatsList[indexPath.row].type == "text"){
                let cell = self.tbvChats.dequeueReusableCell(withIdentifier: "MyChatCell", for: indexPath) as! MyChatCell
                self.fillCell(cell: cell, chat: self.chatsList[indexPath.row])
                
                return cell
            }else {
                let cell = self.tbvChats.dequeueReusableCell(withIdentifier: "MyImgChatCell", for: indexPath) as! MyImgChatCell
                
                self.fillCell(cell: cell, chat: self.chatsList[indexPath.row])
                return cell
            }
            
            
        }else{
            if(self.chatsList[indexPath.row].type == "text"){
                let cell = self.tbvChats.dequeueReusableCell(withIdentifier: "FriendChatCell", for: indexPath) as! FriendsChatCell
                
                self.fillCell(cell: cell, chat: self.chatsList[indexPath.row])
                
                return cell
            }else {
                let cell = self.tbvChats.dequeueReusableCell(withIdentifier: "FriendImgChatCell", for: indexPath) as! FriendImgChatCell
                self.fillCell(cell: cell, chat: self.chatsList[indexPath.row])
                return cell
            }
        }
        
        
    }
    
    
    func fillCell (cell :MyChatCell,chat :ChatVO){
        
        cell.myVO = chat.user
        
        cell.myChat.text = "  "+chat.chat!
        cell.myName.text = chat.user.userName!
        cell.myProfileImg.image = chat.user.profileImg!
        cell.unReadCount.text = "\(chat.unreadCount!)"
    }
    
    func fillCell (cell :FriendsChatCell,chat :ChatVO){
        cell.friendVO = chat.user
        cell.friendChat.text = "  "+chat.chat
        cell.friendName.text = chat.user.userName
        cell.friendProfileImg.image = chat.user.profileImg
        
    }
    func fillCell (cell :MyImgChatCell,chat :ChatVO){
        
        cell.myVO = chat.user
        print("\(self.delegate.serverIp)/\(String(describing: chat.imgUrl!))")
        cell.myImg.image = UIImage(data: try! Data(contentsOf: URL(string: "\(self.delegate.serverIp)/\(String(describing: chat.imgUrl!))")!))
        cell.myName.text = chat.user.userName!
        cell.myProfileImg.image = chat.user.profileImg!
        cell.unReadCount.text = "\(chat.unreadCount!)"
        
    }
    
    func fillCell (cell :FriendImgChatCell,chat :ChatVO){
        cell.friendVO = chat.user
        cell.friendImg.image = UIImage(data: try! Data(contentsOf: URL(string: "\(self.delegate.serverIp)/\(chat.imgUrl!)")!))
        cell.friendName.text = chat.user.userName
        cell.friendProfileImg.image = chat.user.profileImg
        
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        
        
        //==============================
        // 문자열 30글자마다 20의 높이증가
        //==============================
        let height = self.chatsList[indexPath.row].type == "img" ? CGFloat(170) : CGFloat(90)
        print(height)
        return height
        
    }
}



// - MARK: - 네트워크 로직
extension ChatRoomVC{
    func loadRoom( roomId :String){
        
        //roomId , userId , friendId
        let params : Parameters = [
            "roomId":self.currentRoomId,
            "userId":self.delegate.currentUser!._id,
            "friendId" : self.friendId ?? "000000000000000000000000"
            
        ]
        
        let loadRoomReq = Alamofire.request("\(self.delegate.serverIp)/room", method: .post , parameters: params, encoding: JSONEncoding.default)
        var room : RoomVO?
        
        loadRoomReq.responseJSON{
            
            res in
            
            guard let responseJson = res.result.value as? NSDictionary else {
                
                self.alertMaker.makeAlert(title: "서버 오류", message: "서버가 동작하지 않습니다.", VC: self )
                return
            }
            
            let resultCode = responseJson["result"] as! Int
            if(resultCode == 1){
                
                let newRoomJson = responseJson["newRoom"] as! NSDictionary
                let newRoom = RoomVO()
                
                newRoom._id = newRoomJson["_id"] as! String
                
                newRoom.unreadCount = newRoomJson["unreadCount"] as! Int
                
                // newRoom.createAt = newRoomJson["createAt"] as! Date
                newRoom.usersList = self.dataArray(datas: (newRoomJson["usersList"] as! NSArray))
                self.currentRoom = newRoom
                
            }else
            {
                
                let existingRoomJson = responseJson["existingRoom"] as! NSDictionary
                
                let existingRoom = RoomVO()
                
                existingRoom._id = existingRoomJson["_id"] as! String
                
                existingRoom.unreadCount = existingRoomJson["unreadCount"] as! Int
                
                existingRoom.usersList = self.dataArray(datas: (existingRoomJson["usersList"] as! NSArray))
                
                self.currentRoom  = existingRoom
                
            }
            
        }
        
        
    }
    
    
    func firstEntrance(){
        
        let networkeProcesser = NetworkProcesser()
        networkeProcesser.currentUserUpdate()
        
    }
    
    
    
    
}
// - MARK: - 새 채팅
extension ChatRoomVC {
    
    func newChat(userId:String,roomId:String,chat:String){
        let params : Parameters =
            [
                "user": userId,
                "room":roomId,
                "chat":chat
        ]
        
        let newChatReq = Alamofire.request("\(self.delegate.serverIp)/chat/newChat", method: .post, parameters: params, encoding: JSONEncoding.default)
        newChatReq.responseJSON{
            
            res in
            let alertmaker = AlertMaker()
            
            guard let resposeJson = res.result.value as? NSDictionary else{
                //alertmaker.makeAlert(title: "서버오류", message: "서버가 동작하지 않습니다.", VC: self)
                return
            }
            
            let resultCode  =  resposeJson["result"] as! Int
            
            if(resultCode == 1 ){
                
                // 성공
                
                
            }else{
                // 실패
                
                
            }
            
            
        }
        
        
        
    }
    
}
// - MARK: - 채팅 로더
extension ChatRoomVC {
    
    func chatLoader(roomId : String){
        
        self.chatsList.removeAll()
        let chatLoad = Alamofire.request("\(self.delegate.serverIp)/chat/list/\(roomId)", method: .get)
        chatLoad.responseJSON{
            res in
            
            let alertMaker = AlertMaker()
            
            guard let responseJson = res.result.value as? NSDictionary else{
                //alertMaker.makeAlert(title: "서버 오류", message: "서버가 동작하지 않습니다.", VC: self)
                return
            }
            
            let resultCode = responseJson["result"] as! Int
            
            
            if(resultCode == 1){
                
                
                let chats = responseJson["chats"] as! NSArray
                
                for chatObj in chats {
                    
                    let chat = chatObj as! NSDictionary
                    
                    
                    let chatObject = ChatVO()
                    
                    chatObject._id = chat["_id"] as! String
                    chatObject.chat = chat["chat"] as! String
                    
                    let room = chat["room"] as! NSDictionary
                    chatObject.room = room["_id"] as! String
                    chatObject.imgUrl = chat["imgUrl"] as! String
                    chatObject.type = chat["type"] as! String
                    let user = chat["user"] as! NSDictionary
                    chatObject.user = UserVO()
                    chatObject.user._id = user["_id"] as! String
                    chatObject.user.userName = user["userName"] as! String
                    chatObject.user.comment = user["comment"] as! String
                    chatObject.user.profileImgUrl = user["profileImgUrl"] as! String
                    chatObject.unreadCount = chat["unreadCount"] as! Int
                    let profileUrl = "\(self.delegate.serverIp)/\(chatObject.user!.profileImgUrl!)"
                    chatObject.user.profileImg = UIImage(data: try! Data(contentsOf: URL(string: profileUrl)!))
                    chatObject.user.userId = user["userId"] as! String
                    //chatObject.user = chat["user"] as!
                    
                    
                    self.chatsList.append(chatObject)
                }
                
                self.tbvChats.reloadData()
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now()+0.1, execute: {
                    let indexPath = IndexPath(row: self.chatsList.count-1, section: 0)
                    self.tbvChats.scrollToRow(at: indexPath, at: UITableView.ScrollPosition.bottom, animated: true)
                })

                
            }
            
            
            
            
        }
        
    }
    func dataArray(datas:NSArray)->NSMutableArray{
        
        var MutableArray = NSMutableArray()
        for user in datas{
            MutableArray.add(user)
        }
        
        return MutableArray
    }
    
}
extension ChatRoomVC{
    func connectSocket(){
        self.socket = self.socketManager?.socket(forNamespace: "/chat")
        self.socket?.connect()
    }
    func addHandler(){
        self.socket?.on(clientEvent: .connect){
            data,ack in
            self.socket?.emit("init",["roomId":self.currentRoomId])
        }
        self.socket?.on("newChat"){
            data,ack in
            print("새로운 채팅 이벤트 발생")
            self.chatLoader(roomId: self.currentRoomId!)
        }
    }
    
}



extension ChatRoomVC:  UIImagePickerControllerDelegate , UINavigationControllerDelegate{
    // 이미지피커 실행 메소드
    @objc func imgPicker(_ source : UIImagePickerController.SourceType){
        let imgPicker = UIImagePickerController()
        imgPicker.sourceType = source
        imgPicker.delegate = self
        imgPicker.isEditing = true
        self.present(imgPicker,animated: true)
    }
    // 선택후의 로직
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        
        
        if let img = info[UIImagePickerController.InfoKey.originalImage] as? UIImage{
            if let data = img.jpegData(compressionQuality:0.9) {
                let parameters: Parameters = [
                    "room" : self.currentRoomId!,"user":self.delegate.currentUser?._id!
                ]
                
                // You can change your image name here, i use NSURL image and convert into string
              
              //  let fileName = imageURL.absoluteString
                // Start Alamofire
                Alamofire.upload(multipartFormData: { multipartFormData in
                    for (key,value) in parameters {
                        multipartFormData.append((value as! String).data(using: .utf8)!, withName: key)
                    }
                    multipartFormData.append(data, withName: "img",fileName: "upload.png",mimeType: "image/jpeg")
                },
                                 usingThreshold: UInt64.init(),
                                 to: "\(self.delegate.serverIp)/chat/img",
                                 method: .post,
                                 encodingCompletion: { encodingResult in
                                    switch encodingResult {
                                    case .success(let upload, _, _):
                                        upload.responseJSON { response in
                                            self.tbvChats.reloadData()
                                        }
                                    case .failure(let encodingError):
                                        print(encodingError)
                                    }
                })
            }
            UIApplication.shared.isNetworkActivityIndicatorVisible = true
           // self.imgChat(img)
        }
        
        picker.dismiss(animated: true)
        
    }
    func imgChat(_ img:UIImage? , success:(()->Void)? = nil , fail: ((String)->Void)? = nil ){
        
        let url = "http://\(self.delegate.serverIp)/chat/img"
        print(url)
        let headers: HTTPHeaders = [
            /* "Authorization": "your_access_token",  in case you need authorization header */
            "Content-type": "multipart/form-data"
        ]
        let param :[String:AnyObject] = ["room":self.currentRoomId as AnyObject,"user":self.delegate.currentUser?._id as AnyObject]
        
        guard let imageData = img!.jpegData(compressionQuality:1) else {
            print("Could not get JPEG representation of UIImage")
            return
        }
        
        // 2
        Alamofire.upload(multipartFormData: { multipartFormData in
            multipartFormData.append(imageData,
                                     withName: "img",
                                    
                                     mimeType: "image/jpeg")
        },
                         to: "\(self.delegate.serverIp)/chat/img",
                        
                         encodingCompletion: { encodingResult in
        })
    }
    
}
