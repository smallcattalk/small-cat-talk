//
//  Finder.swift
//  SocketChat
//
//  Created by 이동영 on 29/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class Finder: NSObject {
    
    
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    func getRoomId( friendId: String )->(String){
        
        let currentUser = self.delegate.currentUser
    
        let roomsList = currentUser?.roomsList
        
        for room in roomsList! {
            
            if( friendId == (room as! NSDictionary)["friendId"] as! String ){
                return (room as! NSDictionary)["roomId"] as! String
            }
            
        }
        return ""
        
    }
    
    
    

}
