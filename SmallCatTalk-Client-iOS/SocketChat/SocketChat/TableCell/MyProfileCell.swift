//
//  MyProfileCell.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class MyProfileCell: UITableViewCell {

    @IBOutlet var imgMyProfile: UIImageView!
    
    
    @IBOutlet var lbMyName: UILabel!
    
    
    
    @IBOutlet var lbMyComment: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
