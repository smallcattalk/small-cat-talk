//
//  UserVO.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Foundation

class UserVO: NSObject {
    
    // SQLITE ID
    var _id : String?
    
    
    var userId : String!
    
    var userPassword: String?
    
    var userName: String!
    
    var profileImgUrl : String?
    
    // 중앙서버 DB ObjectId
    var inServerId : String?
    
    //
    var friendsList: NSArray?
    
    //
    var roomsList : NSArray?
    
    //
    var inServerFriendListId : String?
    
    //
    var inServerChatListId : String?
    
    var comment : String!
    
    var profileImg : UIImage?
    
    var onChange = false
    
    var personalRoomId = ""

    var status : FRIEND_STATUS?
    
}
