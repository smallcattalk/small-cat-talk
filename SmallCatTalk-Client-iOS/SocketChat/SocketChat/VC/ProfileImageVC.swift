//
//  ProfileImageVCViewController.swift
//  SocketChat
//
//  Created by 이동영 on 29/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class ProfileImageVC: UIViewController {

    @IBOutlet var btnClose: UIButton!
    
    var img : UIImage?
    
    @IBOutlet var profileImg: UIImageView!
    
    
    @IBAction func btnClose(_ sender: Any) {
        
        self.dismiss(animated: true)
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.profileImg!.image = img!

        // Do any additional setup after loading the view.
    }
    

  

}
