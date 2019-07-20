//
//  FriendsProfileCell.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class FriendsProfileCell: UITableViewCell {

    @IBOutlet var imgFreind: UIImageView!
    @IBOutlet var lbFriendName: UILabel!
    @IBOutlet var lbFriendComment: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
