//
//  Session.swift
//  SocketChat
//
//  Created by 이동영 on 23/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Foundation


class Session: NSObject {

    func load()->UserVO?{
        let plist = UserDefaults.standard
        guard let _id = plist.object(forKey: "_id") as? String else{
            return nil
        }
        guard let userName = plist.object(forKey: "userName") as? String else{
            return nil
        }
        guard let userId = plist.object(forKey: "userId") as? String else{
            return nil
        }
        guard let profileImgUrl = plist.object(forKey: "profileImgUrl") as? String else{
            return nil
        }
        guard let comment = plist.object(forKey: "comment") as? String else{
            return nil
        }
        guard let friendsList = (plist.object(forKey: "friendList") as? NSArray) else{
            return nil
        }
        guard let roomsList = plist.object(forKey: "roomsList") as? NSArray else{
            return nil
        }
        
        let loadUser = UserVO()
        loadUser._id = _id
        loadUser.userName = userName
        loadUser.userId = userId
        loadUser.profileImgUrl = profileImgUrl
        loadUser.comment = comment
        loadUser.friendsList = friendsList
        loadUser.roomsList = roomsList
        return loadUser
    }
    
    
    func save(authUser : UserVO){
        let delegate = UIApplication.shared.delegate as! AppDelegate
        delegate.currentUser = authUser
        let plist = UserDefaults.standard
        plist.setValue(authUser._id, forKeyPath: "_id")
        plist.setValue(authUser.userId, forKeyPath: "userId")
        plist.setValue(authUser.userName, forKeyPath: "userName")
        plist.setValue(authUser.comment, forKeyPath: "comment")
        plist.setValue(authUser.profileImgUrl, forKeyPath: "profileImgUrl")
        plist.setValue(authUser.friendsList, forKeyPath: "friendList")
        plist.setValue(authUser.roomsList, forKeyPath: "roomsList")
        plist.synchronize()
    }
    
    
    
    
}
