//
//  JoinVC.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Alamofire



class JoinVC: UIViewController,UITextFieldDelegate {
    
    
    // 전역객체
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    var keyboardControl : KeyboardControl?
    
    @IBOutlet var txfId: UITextField!
    
    @IBOutlet var txfName: UITextField!
    
    var textFields : [UITextField] = []
    
    @IBOutlet var txfPassword1: UITextField!
    
    @IBOutlet var txfPassword2: UITextField!
    
    @IBOutlet var bgView: UIView!
    
    
    @IBOutlet var btnJoin: UIButton!
    
    @IBOutlet var btnCancel: UIButton!
    
    
    var checkId : Bool = false
    
    @IBAction func btnJoin(_ sender: Any) {
        
        for txf in self.textFields{
           if txf.text == ""
           {
            makeAlert(title: "가입 실패", message: "빈 칸을 채워 주세요.")
            }
        }
        
        let newUser = UserVO()
        
        newUser.userId = self.txfId.text
        newUser.userName = self.txfName.text
        newUser.userPassword = self.txfPassword1.text
        
        self.join(newUser: newUser)
        
        
    }
    @IBOutlet var warning: UILabel!
    
    @IBAction func btnCancel(_ sender: Any) {
        self.dismiss(animated: true)
    }
    
    
    @IBAction func btnCheck(_ sender: Any) {
        self.check(userId: self.txfId.text!)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.textFields.append(self.txfId)
        self.textFields.append(self.txfName)
        self.textFields.append(self.txfPassword1)
        
         self.textFields.append(self.txfPassword2)
         self.textFields.append(self.txfId)
        
        
        self.btnJoin.clipsToBounds = true
        self.btnJoin.layer.cornerRadius = 5
        
        self.btnCancel.clipsToBounds = true
        self.btnCancel.layer.cornerRadius = 10
        
        self.txfId.placeholder = "아이디"
        self.txfName.placeholder = "사용자 이름"
        self.txfPassword1.placeholder = "비밀번호"
        self.txfPassword2.placeholder = "비밀번호 확인"
        
        self.txfPassword1.isSecureTextEntry = true
        self.txfPassword2.isSecureTextEntry = true
   
       for txf in self.textFields {
            txf.delegate = self
        }
        
        keyboardControl = KeyboardControl(self.textFields, view: self.bgView, height: 150,duration:1,VC:self)
  
        
    }
    
   
    
    
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    @IBAction func blurKeyBoard(_ sender: Any) {
        
        for txf in self.textFields {
            if(txf.isFirstResponder){
                txf.resignFirstResponder()
                break;
            }
        }
    }
    
    
  

    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField == self.txfId){
            self.checkId = false
        }else if (textField == self.txfPassword1 || textField == self.txfPassword2){
        let newText = "\(textField.text!)\(string)"

            print(newText)
        self.warning.text = self.txfPassword1.text == newText ? "":"비밀번호가 일치하지 않습니다."
        }
        return true
    }
 
    
    
    func makeAlert(title:String,message:String,action:((UIAlertAction)->Void)? = nil){
        
        
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "확인", style: .default , handler:action))
        
        
        self.present(alert,animated: true)
        
    }
    

    
}
// - MARK: 네트워크 로직

extension JoinVC{
  
    func join(newUser:UserVO){
        
      //  self.checkRex(userId: newUser.userId!)
        
        if(!self.checkId){
            self.makeAlert(title: "가입 요청 실패", message: "아이디 확인을 마쳐주세요.")
            return
        }
        
        if(self.warning.text != ""){
            self.makeAlert(title: "가입 요청 실패", message: "비밀번호가 일치하지 않습니다.")
            return
        }
        
        if(self.txfPassword1.text!.count < 4){
            self.makeAlert(title: "가입 요청 실패", message: "4자리 이상의 비밀번호를 사용해주세요.")
            return
        }
        
        
        let params : Parameters =
            [ "userId" : newUser.userId,
              "userName":newUser.userName,
              "userPassword":newUser.userPassword!]
        
        let joinReq = Alamofire.request("\(delegate.serverIp)/join", method: .post, parameters: params, encoding: JSONEncoding.default)
        
        joinReq.responseJSON{
            res in
            
            print("==================================================\n\(res)")
            guard let json = res.result.value as? NSDictionary else{
                self.makeAlert(title: "서버 에러", message: "서버로 부터 응답이 없습니다.")
                return
            }
            
            let result = json["result"] as! Int
            
            switch(result){
            case 0 :
                self.makeAlert(title: "가입 실패", message: "등록된 유저 입니다.")
            case 1 :
                self.makeAlert(title: "회원 가입 성공", message: "환영합니다 ! \(newUser.userName!)님"){
                     _ in
                    self.dismiss(animated: true, completion: nil)
                }
                
            default:
                ()
            }
            
         
        }
        
    }
    
    func check( userId : String){
     
        
      self.checkRex(userId: userId)
        
        if(!self.checkId){
            self.makeAlert(title: "요청 실패", message: "4글자이상의 영어와 숫자로 구성하여 주세요.")
            return
        }
        let checkReq = Alamofire.request("\(self.delegate.serverIp)/check/\(userId)", method: .get)
        
        checkReq.responseJSON{
            res in
            
            print("==================================================\n\(res)")
            guard let json = res.result.value as? NSDictionary else{
                self.makeAlert(title: "서버 에러", message: "서버로 부터 응답이 없습니다.")
                return
            }
            
            let result = json["result"] as! Int
            
            switch(result){
            case 0 :
                self.makeAlert(title: "아이디 체크", message: "사용 중인 아이디입니다.")
            case 1 :
                self.makeAlert(title: "아이디 체크", message: "사용 가능한 아이디입니다.")
                self.checkId = true
                
            default:
                ()
            }
            
            
        }
    }
    
    func checkRex( userId : String){
        //let str = "1234567890"
       /* let pattern = "/^[a-z0-9_-]{3,16}$/"
        let regex = try! NSRegularExpression(pattern:pattern, options:[])
        
        
       /* if (((regex.firstMatch(in: userId, options: [], range:NSRange(location: 0, length: */
        if(((regex.matches(in: userId, options: [], range: NSRange(location: 0, length: userId.count))) != nil)){
        
               self.makeAlert(title: "아이디 검사 에러", message: "영어와 숫자만 포함해주세요.")
                self.checkId = false
             return
                
        }*/
        
      //  }
        userId.map{
            char in
            if(char == " "){
                self.makeAlert(title: "아이디 에러", message: "공백이 포함됩니다.")
                return
            }
        }
        
        if(userId.getArrayAfterRegex(regex: "[a-zA-Z0-9]{4,}").count == 1){
            self.checkId = true
            
        }else{
            self.checkId  = false
        }
        
    }
}
extension String{
    func getArrayAfterRegex(regex: String) -> [String] {
        
        do {
            let regex = try NSRegularExpression(pattern: regex)
            let results = regex.matches(in: self,
                                        range: NSRange(self.startIndex..., in: self))
            return results.map {
                String(self[Range($0.range, in: self)!])
            }
        } catch let error {
            print("invalid regex: \(error.localizedDescription)")
            return []
        }
    }
}
