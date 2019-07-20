//
//  FriendsChatCell.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class FriendsChatCell: UITableViewCell {

    
    var friendVO : UserVO?
    
    @IBOutlet var friendName: UILabel!
    
    
    @IBOutlet var friendProfileImg: UIImageView!
    
    @IBOutlet var friendChat: UILabel!
    
    @IBOutlet var view: UIView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // self.friendChat
        self.friendChat.clipsToBounds = true
        
        self.friendChat.layer.cornerRadius  = 15
        self.friendChat.text?.count
        view.transform = CGAffineTransform(rotationAngle: CGFloat(45 * M_PI / 180))
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
