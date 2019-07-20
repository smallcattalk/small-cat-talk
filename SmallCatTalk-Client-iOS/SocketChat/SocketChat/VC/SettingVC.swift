//
//  SettingVC.swift
//  
//
//  Created by 이동영 on 23/03/2019.
//

import UIKit

class SettingVC: UITableViewController {
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    // MARK: - Table view data source
    
    
    
    
    
    func logout(){
        
        self.delegate.currentUser = nil
        self.delegate.logIn = false
        
        let plist  = UserDefaults.standard
        
        plist.setValue(nil, forKeyPath: "_id")
        plist.setValue(nil, forKeyPath: "userId")
        plist.setValue(nil, forKeyPath: "userName")
        plist.setValue(nil, forKeyPath: "comment")
        plist.setValue(nil, forKeyPath: "profileImgUrl")
        plist.setValue(nil, forKeyPath: "friendList")
        
        plist.synchronize()
        
        self.makeAlert(title: "로그아웃 합니다", message: ""){
            _ in
            
            self.tabBarController?.selectedIndex = 0;
        }
        
        
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 1
    }
    
    
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "SettingCell", for: indexPath)  as! SettingCell
        cell.imgMenu.image =
            UIImage(named: "exit.png")
        
        cell.menu.text = "로그아웃"
        
        // Configure the cell...
        
        return cell
    }
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if(indexPath.row == 0 ){
            
            self.logout()
        }
    }
    
    
    
    
    func makeAlert(title:String,message:String,action:((UIAlertAction)->Void)? = nil){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "확인", style: .default , handler:action))
        self.present(alert,animated: true)
    }
    
}
