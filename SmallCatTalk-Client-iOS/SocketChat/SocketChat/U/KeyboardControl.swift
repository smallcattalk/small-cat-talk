//
//  KeyboardControl.swift
//  SocketChat
//
//  Created by 이동영 on 23/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import Foundation
import UIKit
import NotificationCenter

class KeyboardControl :NSObject,UITextFieldDelegate{
    var textFields : [UITextField]?
    var targetView : UIView?
    var keyBoardheight : CGFloat?
    var duration :Double?
    var except : UIViewController?

    init(_ txfs: [UITextField], view:UIView , height : CGFloat ,duration:Double, VC:UIViewController? = nil) {
        
        super.init()
        self.duration = duration
        self.textFields = txfs
        self.targetView = view
        self.keyBoardheight = height
        for txf in self.textFields! {
            txf.delegate = self
        }
        if(VC != nil ){self.except = VC}
    }
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        self.keyboardWillShow(view: self.targetView!)
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let VC = self.except as? JoinVC else {
            print("변환실패")
            return true
        }
        if(textField == VC.txfId){
            VC.checkId = false
        }else if (textField == VC.txfPassword1 || textField == VC.txfPassword2){
            let newText = "\(textField.text!)\(string)"
            VC.warning.text = VC.txfPassword1.text == newText ? "":"비밀번호가 일치하지 않습니다."
        }
        return true
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.keyboardWillHide(view: self.targetView!)
    }
    
    func keyboardWillHide( view: UIView){
        UIView.animate(withDuration: self.duration! ){
            view.bounds.size.height -= self.keyBoardheight!
        }
    }
    
    func keyboardWillShow(view: UIView){
        UIView.animate(withDuration: self.duration! ){
            view.bounds.size.height+=self.keyBoardheight!
        }
    }
}

// - MARK: - JOINVC
extension KeyboardControl{
    func blurKeyBoard(_ sender: Any) {
        for txf in self.textFields! {
            if(txf.isFirstResponder){
                txf.resignFirstResponder()
                break;
            }
        }
    }
}

