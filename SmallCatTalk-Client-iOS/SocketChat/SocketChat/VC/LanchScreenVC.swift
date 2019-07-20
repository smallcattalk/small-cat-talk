//
//  LanchScreenVC.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit

class LanchScreenVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.txfHello.font = UIFont(name: "BM-JUA", size: 26)
        self.txfTitle.font = UIFont(name: "BM-JUA", size: 22)
    }
    @IBOutlet var txfHello: UILabel!
    
    @IBOutlet var txfTitle: UILabel!
  
    

}
