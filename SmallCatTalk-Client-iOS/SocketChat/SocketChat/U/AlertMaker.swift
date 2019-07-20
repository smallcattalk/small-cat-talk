//
//  AlertMaker.swift
//  SocketChat
//
//  Created by 이동영 on 23/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class AlertMaker: NSObject {
    func makeAlert(title:String,message:String, VC:UIViewController ,action:((UIAlertAction)->Void)? = nil){
        
        
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "확인", style: .default , handler:action))
        
        VC.present(alert,animated: true)
        
    }
}
