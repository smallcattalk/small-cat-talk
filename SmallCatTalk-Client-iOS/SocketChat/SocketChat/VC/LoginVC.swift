//
//  LoginVC.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Alamofire

class LoginVC: UIViewController {
    
    // 전역객체
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    @IBOutlet var txfId: UITextField!
    
    @IBOutlet var txfPassword: UITextField!
    
    @IBOutlet var btnJoin: UIButton!
    
    @IBOutlet var btnLogin: UIButton!
    
    var textFields:[UITextField] = []
    
    var keyboardControll : KeyboardControl?
  
    override func viewDidLoad() {
        super.viewDidLoad()
        self.textFields.append(self.txfId)
        self.textFields.append(self.txfPassword)
        self.btnJoin.clipsToBounds = true
        self.btnJoin.layer.cornerRadius = 10
        self.btnLogin.clipsToBounds = true
        self.btnLogin.layer.cornerRadius = 10
        self.txfId.placeholder = "아이디"
        self.txfPassword.placeholder = "패스워드"
        self.txfPassword.isSecureTextEntry = true
        keyboardControll = KeyboardControl(self.textFields, view: self.view, height: 150,duration:1)
    }
    
    @IBOutlet var lbLogin: UILabel!
    
    
    @IBAction func blurKeyBoard(_ sender: Any) {
        for txf in self.textFields {
            if(txf.isFirstResponder){
                txf.resignFirstResponder()
                break
            }
        }
    }
    

    @IBAction func btnJoin(_ sender: Any) {
       
    }
    
    
    @IBAction func btnLogin(_ sender: Any) {
        
        for txf in self.textFields {
            guard txf.text != "" else{
                self.makeAlert(title: "로그인 실패", message: "아이디/비밀번호를 입력해주세요.")
                return
            }
        }
        let userId = self.txfId.text!
        let userPassword = self.txfPassword.text!
        self.login(userId: userId, userpassword: userPassword)
    }
    
    
    func makeAlert(title:String,message:String,action:((UIAlertAction)->Void)? = nil){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "확인", style: .default , handler:action))
        self.present(alert,animated: true)
    }
    

}
// - MARK: - 네트워크 로직
extension LoginVC{
    func login(userId:String,userpassword:String){
        let params : Parameters =
            [
             "userId":userId,
             "userPassword":userpassword]
        let loginReq = Alamofire.request("\(self.delegate.serverIp)/login", method: .post, parameters: params, encoding: JSONEncoding.default)
        loginReq.responseJSON{
            res in
            guard let json = res.result.value as? NSDictionary else{
                self.makeAlert(title: "서버 오류", message: "서버가 응답하지 않습니다.")
                return
            }
            let jsonResult = json["result"] as! Int
            var resultCode: resultCode = .DB_ERROR
            
            switch(jsonResult){
            case -2 :
                resultCode = .DB_ERROR
            case -1:
                resultCode = .PASSWORD_ERROR
            case 0 :
                resultCode = .USER_NOT_FOUND
            case 1:
                resultCode = .SUCCESS
            default:
                ()
            }
            
            switch(resultCode){
                
            case .DB_ERROR:
                self.makeAlert(title: "로그인 실패", message: "데이터 베이스 오류")
            case .PASSWORD_ERROR:
                self.makeAlert(title: "로그인 실패", message:"패스워드 불일치")
            case .USER_NOT_FOUND:
                self.makeAlert(title: "로그인 실패", message:"가입되지 않은 유저")
            case .SUCCESS:
                let  userJson  =  json["user"] as! NSDictionary
                let authUser = UserVO()
                authUser._id = (userJson["_id"] as! String)
                authUser.userId = (userJson["userId"] as! String)
                authUser.userName = (userJson["userName"] as! String)
                authUser.comment = (userJson["comment"] as! String)
                authUser.friendsList = userJson["friendsList"] as! NSArray
                authUser.roomsList = userJson["roomsList"] as! NSArray
                authUser.profileImgUrl = (userJson["profileImgUrl"] as! String)
             
            /*    let plist = UserDefaults.standard
                plist.setValue(authUser._id, forKeyPath: "_id")
                plist.setValue(authUser.userId, forKeyPath: "userId")
                plist.setValue(authUser.userName, forKeyPath: "userName")
                plist.setValue(authUser.comment, forKeyPath: "comment")
                plist.setValue(authUser.profileImgUrl, forKeyPath: "profileImgUrl")
        
                plist.setValue(authUser.friendList, forKeyPath: "friendList")
                plist.synchronize()
           */
                let save = Session()
                save.save(authUser: authUser)
                self.makeAlert(title: "로그인 성공", message:"\(authUser.userName!)님 환영합니다."){
                _ in
                self.dismiss(animated: true)
                }
            }
            
      
            
            
        }
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        for txf in self.textFields{
            txf.text = nil
        }
    }
    
}
enum resultCode: Int{
   
    case DB_ERROR = -2
    case PASSWORD_ERROR = -1
    case USER_NOT_FOUND = 0
    case SUCCESS = 1
    
}
