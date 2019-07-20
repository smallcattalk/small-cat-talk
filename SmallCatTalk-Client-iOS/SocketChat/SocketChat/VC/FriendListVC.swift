//
//  FriendListVC.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Alamofire


class FriendListVC: UITableViewController {
    
    var enterChatRoom = false
    
    var alertMaker:AlertMaker?

    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    var friendsList : [UserVO] = []
    
    var currentUser: UserVO? {
        return delegate.currentUser
    }
    
    var selectedPersonalRoomId : String?
    
    var selectedRoom : RoomVO?
    
    var chatFriendId : String?
 
    var toggle : Bool = false
    
    var login :Bool = false  {
        
        willSet(BEFORE){
            self.toggle = BEFORE
        }
        
        didSet(AFTER){
            if(AFTER != self.toggle ){
                self.loadFriends()
                self.tableView.reloadData()
            
            }
        }
       
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        if(self.enterChatRoom){
            
         
            let roomListNaviVC = self.tabBarController?.viewControllers![1] as! UINavigationController
            
            var chatRoomVC = storyboard?.instantiateViewController(withIdentifier: "ChatRoomVC") as! ChatRoomVC
            
          //  self.loadRoom(roomId: self.selectedPersonalRoomId!)
            
            chatRoomVC.currentRoomId = self.selectedPersonalRoomId!
            
            chatRoomVC.friendId = self.chatFriendId
            
            roomListNaviVC.pushViewController(chatRoomVC, animated: false)
            
            self.tabBarController?.selectedIndex = 1
            self.enterChatRoom = false
            
            
            
        }
        
        if(self.delegate.currentUser == nil)
        {
        let LoginVC = storyboard?.instantiateViewController(withIdentifier: "LoginVC") as! LoginVC
        self.present(LoginVC,animated: true)
            
        }else{
         
            if((self.currentUser?.onChange)!){
                
                self.loadFriends()
                self.tableView.reloadData()
                self.currentUser?.onChange = false
            }
           
        }
     self.login = self.delegate.logIn
       
    }
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
     
        self.tableView.sectionIndexBackgroundColor = UIColor.yellow
        
        self.alertMaker = AlertMaker()
        
        if(self.delegate.currentUser == nil)
        {
        }else{
            self.makeAlert(title: "환영합니다 ! ", message: "\(self.delegate.currentUser!.userName!) 님 !")
            
        }

    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        if(self.friendsList.count != 0){
            return 2
            
        }
        else{
            return 1
        }
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        
        if section == 0 {
            return 1
        }
        
        return self.friendsList.count
    }
    @IBAction func btnAddFriend(_ sender: Any) {
        
    }
    
    @IBAction func btnSearchFriend(_ sender: Any) {
        
    }
    
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        switch section {
        case 0:
            return "나의 프로필"
        case 1:
            return "친구 목록"
        default:
            return ""
        }
    }
   
   
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard self.delegate.currentUser != nil else {
            let cell =   self.tableView.dequeueReusableCell(withIdentifier: "MyProfileCell", for: indexPath)
            return cell
       }
      
       let section = indexPath.section
       if(section == 0){
        let cell =   self.tableView.dequeueReusableCell(withIdentifier: "MyProfileCell", for: indexPath) as! MyProfileCell
        let me = self.delegate.currentUser!
            
            self.fillCell(cell,me)
            return cell
            
        }else{
            let cell =   self.tableView.dequeueReusableCell(withIdentifier: "FriendsProfileCell", for: indexPath) as! FriendsProfileCell
            let friend = self.friendsList[indexPath.row]
            
            self.fillCell(cell,friend)
            
            return cell
        }
 }
    
    
    func fillCell(_ myProfileCell:MyProfileCell, _ me:UserVO){
       if me.profileImg == nil {
        me.profileImg = self.loadImg(url: me.profileImgUrl!)
        }
        myProfileCell.lbMyName.text = me.userName
        myProfileCell.imgMyProfile.image = me.profileImg
        myProfileCell.lbMyComment.text  = me.comment
    }
    
    func fillCell(_ friendsProfileCell: FriendsProfileCell , _ friend : UserVO){
        
        if friend.profileImg == nil {
            friend.profileImg = self.loadImg(url: friend.profileImgUrl! )
        }
        friendsProfileCell.lbFriendName.text = friend.userName
        friendsProfileCell.imgFreind.image = friend.profileImg
        friendsProfileCell.lbFriendComment.text  = friend.comment
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
  
        self.tableView.deselectRow(at: indexPath, animated: true)
        performSegue(withIdentifier: "UserDetailSegue", sender: (indexPath.section,indexPath.row))
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "UserDetailSegue"){
            let  (section , index ) = sender  as! (Int,Int)
            
            let userDetailVC = segue.destination as! UserDetailVC
            
            if(section == 0){
                userDetailVC.selectedUser = self.currentUser
            }else{
               // userDetailVC.alreadyFriend = true
                 userDetailVC.selectedUser = self.friendsList[index]
            }
        }
    }
    
   
    func makeAlert(title:String,message:String,action:((UIAlertAction)->Void)? = nil){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "확인", style: .default , handler:action))
        self.present(alert,animated: true)
        }
}
// - MARK: - 네트워크 로직
extension FriendListVC {
    func loadImg( url :String) -> UIImage{
        
        let imgUrl = "\(self.delegate.serverIp)/\(url)"
        
        let profileImg = UIImage(data: try! Data(contentsOf: URL(string: imgUrl )! ))
        
        return profileImg!
    }
    
    func loadFriends(){
        
        self.friendsList.removeAll()
        
        let param : Parameters =
            [
                "_id":self.currentUser?._id
        ]
        
        let friendUrl = "\(self.delegate.serverIp)/friend/list"
        
        let friendsListRequest = Alamofire.request(friendUrl, method: .post, parameters: param, encoding: JSONEncoding.default)
        
        friendsListRequest.responseJSON{
            res in
        
            guard let Json = res.result.value as? NSDictionary else {
               //self.alertMaker?.makeAlert(title: " ERROR ", message: "에러 핸들링", VC: self)
                return
            }
            guard let result = Json["result"] as? Int else{
                self.alertMaker?.makeAlert(title: "서버 오류", message: "서버가 동작하지 않습니다.", VC: self)
                return
            }
            if(result == 0){
                self.alertMaker!.makeAlert(title: "로드 실패.", message: "친구목록이 없습니다.",VC: self)
            }else{
            var friendsList = Json["friendsList"] as! [NSDictionary]
            
            for friend in friendsList {
                let loadedFriend = UserVO()
                
                loadedFriend._id = (friend["_id"] as! String)
                loadedFriend.userId = (friend["userId"] as! String)
                loadedFriend.comment = (friend["comment"] as! String)
                loadedFriend.profileImgUrl = (friend["profileImgUrl"] as! String)
                loadedFriend.userName = (friend["userName"] as! String)
                loadedFriend.profileImg = self.loadImg(url: loadedFriend.profileImgUrl!)
                    // UIImage(data: try! Data(contentsOf: URL(string: loadedFriend.profileImgUrl!)! ))
                self.friendsList.append(loadedFriend)
      }
                 self.tableView.reloadData()
                
 
            
            
            }
        }
        
        
        
    }
    
    
    
    
    @IBAction func openChatRoomFromUserDetailVC( _ segue: UIStoryboardSegue ){

        
        self.enterChatRoom  = true
        

       self.chatFriendId  = (segue.source as! UserDetailVC).selectedUser?._id!

        self.selectedPersonalRoomId = self.getRoomId(userId: self.chatFriendId!)
        
        
        
        print("눌린 유저의 개인방: \(self.selectedPersonalRoomId!)")
        
    }
    
    
    
    
    
    
    func getRoomId(userId:String)->(String){
        
        
        let roomsList = self.currentUser?.roomsList
        
        
        
        for room in roomsList! {
            
            
            
            let roomObject = room as! NSDictionary
        
            let friendId = roomObject["friendId"] as! String
            
            if(friendId == userId){
                return roomObject["roomId"] as! String
            }
            
        }
        
        return ""
        
        
    }
    
}
