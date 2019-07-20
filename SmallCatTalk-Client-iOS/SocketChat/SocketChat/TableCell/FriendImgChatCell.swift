//
//  FriendImgChatCell.swift
//  SocketChat
//
//  Created by 이동영 on 12/05/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class FriendImgChatCell: UITableViewCell {

    
    
    
    
    @IBOutlet var view: UIView!
    var friendVO : UserVO?
    
    @IBOutlet var friendName: UILabel!
    
    
    @IBOutlet var friendProfileImg: UIImageView!
    
    @IBOutlet var friendImg: UIImageView!
    
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
       view.transform = CGAffineTransform(rotationAngle: CGFloat(45 * M_PI / 180))
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
