//
//  RoomCell.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class RoomCell: UITableViewCell {
    
    
    @IBOutlet var roomImg: UIImageView!
    
    @IBOutlet var roomTitle: UILabel!
    
    @IBOutlet var lastChat: UILabel!
    
    
    @IBOutlet var unReadMeassageCount: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
     self.unReadMeassageCount.clipsToBounds = true
        self.unReadMeassageCount.bounds.size =
            CGSize(width: self.unReadMeassageCount.bounds.width, height: self.unReadMeassageCount.bounds.width)
        self.unReadMeassageCount.layer.cornerRadius = self.unReadMeassageCount.bounds.width/2
        
        
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
