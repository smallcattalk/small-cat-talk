//
//  RoomVO.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class RoomVO: NSObject {

    
    var _id :String?
    var lastChat : ChatVO?
    var usersList : NSMutableArray?
    var unreadCount:Int?
    var createAt:Date?
    
    var friend : UserVO?

    
    
    
}
