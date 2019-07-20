//
//  UserDetailVC.swift
//  SocketChat
//
//  Created by 이동영 on 23/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import Foundation
import UIKit
import Alamofire


class UserDetailVC : UIViewController{
    
    var selectedUser : UserVO?
    
    
    var onSearch = false
 //   var alreadyFriend  = false

    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    let alertMaker = AlertMaker()
    
    
    var createChat = false
    
    override func viewWillAppear(_ animated: Bool) {
      
        
        self.imgProfile.clipsToBounds = true
        self.imgProfile.layer.cornerRadius = self.imgProfile.bounds.size.width/2
    
        self.imgProfile.image = self.selectedUser?.profileImg
        
        self.lbName.text = self.selectedUser?.userName!
        
        
        self.lbComment.text = self.selectedUser?.comment!
        
        
        self.btnOpenChat.clipsToBounds = true
        self.btnOpenChat.layer.cornerRadius = self.btnOpenChat.bounds.size.width/2
        
        self.btnAddFriend.clipsToBounds = true
        self.btnAddFriend.layer.cornerRadius = self.btnAddFriend.bounds.size.width/2
    
        
        self.friendStatus()
        
        self.buttonControl()
        
   
        
        
        if(onSearch){
            if(self.selectedUser?.status == .ME){
                 self.view.backgroundColor = UIColor.init(displayP3Red: 1, green: 0.94902, blue:0.466667, alpha: 0.3)
                self.lbComment.text = " 본인 입니다."
            }
            
            if (self.selectedUser?.status == .EXIST){
                 self.view.backgroundColor = UIColor.init(displayP3Red: 1, green: 0.94902, blue:0.466667, alpha: 0.3)
            self.lbComment.text = " 이미 친구입니다. "
            }
            
        }
        
    }
    
    override func viewDidLoad() {
        
       
    }
    
    
    @IBAction func profileImgClick(_ sender: Any) {
        
        let ProfileImageVC = storyboard?.instantiateViewController(withIdentifier: "ProfileImageVC") as! ProfileImageVC
        
        ProfileImageVC.img = self.selectedUser?.profileImg
        
        self.present(ProfileImageVC, animated: true)
        
        
    }
    
    
    
    
    @IBOutlet var btnClose: UIButton!
    
    @IBOutlet var btnAddFriend: UIButton!
    
    @IBOutlet var imgProfile: UIImageView!
    
    @IBOutlet var lbName: UILabel!
    
    @IBOutlet var lbComment: UILabel!
    
    @IBOutlet var btnOpenChat: UIButton!
    
    @IBAction func btnClose(_ sender: Any) {
    
        self.dismiss(animated: true)
    
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
    }
    
    @IBAction func openChat(_ sender: Any) {
       
    }
    
    
    @IBAction func btnAddFriend(_ sender: Any) {
        
        self.addFriend(_id: (self.selectedUser?._id)!)
        
    }
    
    
}
// - MARK: - 네트워크 로직
extension UserDetailVC{
    
    func addFriend( _id:String){
        
        let url = "\(self.delegate.serverIp)/friend"
        
        
        let friendId = _id
        
        let param  : Parameters = [
            "_id": self.delegate.currentUser?._id! ,
            "friendId":friendId
        ]
        
        let addFriendRequest = Alamofire.request(url, method: .post, parameters: param, encoding: JSONEncoding.default)
        
        addFriendRequest.responseJSON{
            
            res in
            self.delegate.currentUser?.onChange = true
            guard let resultJson = res.result.value as? NSDictionary else{
                
                self.alertMaker.makeAlert(title: "서버 에러 ", message: "서버가 응답하지 않습니다", VC: self)
                return
            }
            
            let result = resultJson["result"] as! Int

            if(result == 1){
            
              let user =   self.delegate.currentUser!
                
                user.friendsList?.adding(resultJson["newFriend"] as! NSString)
                
                user.onChange = true
                
                self.delegate.currentUser = user
                
                self.alertMaker.makeAlert(title: "친구 추가  ", message: "+ +", VC: self ){
                    
                    _ in
                    let networkProcesser = NetworkProcesser()
                    networkProcesser.currentUserUpdate()
                    self.dismiss(animated: true)
                    
                    }
                    
                }
            }
        }
    }
    
// - MARK: - 네트워크 로직
extension UserDetailVC{
    
    func buttonControl(){
        
        print(self.selectedUser!.status)
        switch( self.selectedUser!.status){
       
        case .NEW?:
            self.btnOnOff(button: self.btnAddFriend, enable: true)
            self.btnOnOff(button: self.btnOpenChat, enable: false)
        case .ME?:
                self.btnOnOff(button: self.btnAddFriend, enable: false)
                self.btnOnOff(button: self.btnOpenChat, enable: false)
        case .EXIST?:
            self.btnOnOff(button: self.btnAddFriend, enable: false)
            self.btnOnOff(button: self.btnOpenChat, enable: true)
     case .none:
            ()
        }
    }
    
    func btnOnOff( button : UIButton , enable:Bool){
        
         button.isEnabled = enable
        if(enable){
              button.backgroundColor  = UIColor.init(displayP3Red: 1, green: 0.94902, blue:0.466667, alpha: 1)
        }else{
                  button.backgroundColor  = UIColor.init(displayP3Red: 0.3, green: 0.3, blue:0.3, alpha: 0.3)
        }
    }
    
    func friendStatus(){
        print(self.delegate.currentUser!.friendsList as! Array<NSDictionary>)
        print(selectedUser!._id)
        if(self.delegate.currentUser!._id == self.selectedUser!._id){
            self.selectedUser?.status = .ME
            return
        }else{
            
            for friendId in ((self.delegate.currentUser!.friendsList)! as! Array<NSDictionary>) {
                print(friendId)
                if(((friendId as! NSDictionary)["_id"] as! String) == self.selectedUser!._id){
                    self.selectedUser!.status = .EXIST
                    return
                }
            }
            self.selectedUser!.status = .NEW
        }
    }
    
}
enum FRIEND_STATUS : Int{
    case ME = -1
    case EXIST = 0
    case NEW = 1
}

