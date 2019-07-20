//
//  MyImgChatCell.swift
//  SocketChat
//
//  Created by 이동영 on 12/05/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class MyImgChatCell: UITableViewCell {

    var myVO : UserVO?
    
    @IBOutlet var myProfileImg: UIImageView!
    
    @IBOutlet var myImg: UIImageView!
    
    @IBOutlet var myName: UILabel!
    
    @IBOutlet var unReadCount: UILabel!
    
    
    
    
    @IBOutlet var view: UIView!
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
