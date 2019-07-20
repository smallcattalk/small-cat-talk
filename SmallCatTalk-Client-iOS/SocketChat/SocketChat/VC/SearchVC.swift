//
//  SearchVC.swift
//  SocketChat
//
//  Created by 이동영 on 25/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import Alamofire



class SearchVC: UIViewController {
    
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    var searchResultList = [UserVO]()
    
    let alertMaker = AlertMaker()
    
    
    override func viewWillAppear(_ animated: Bool) {
        if(self.delegate.currentUser == nil ){
            self.dismiss(animated: false)
            
            
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        //self.dismiss(animated: false)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.searchBar.delegate = self
        self.searchBar.keyboardType = UIKeyboardType.alphabet
        self.tbvUserList.dataSource = self
        self.tbvUserList.delegate = self
        // Do any additional setup after loading the view.
    }
    
    @IBOutlet var tbvUserList: UITableView!
    
    @IBOutlet var searchBar: UISearchBar!
    
    
}
// - MARK: - 테이블뷰 델리게이트
extension SearchVC : UITableViewDataSource,UITableViewDelegate{
    
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.searchResultList.count
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = self.tbvUserList.dequeueReusableCell(withIdentifier: "SearchUserCell", for: indexPath) as! FriendsProfileCell
        
        cell.imgFreind.image = self.searchResultList[indexPath.row].profileImg
        cell.lbFriendComment.text = self.searchResultList[indexPath.row].comment
        cell.lbFriendName.text = self.searchResultList[indexPath.row].userName
        
        return cell
        
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        self.tbvUserList.deselectRow(at: indexPath, animated: true)
        
        
        performSegue(withIdentifier: "UserDetailVCSegueFromSearch", sender: indexPath)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "UserDetailVCSegueFromSearch"){
            
            let userDetailVC = segue.destination as! UserDetailVC
            
            userDetailVC.onSearch = true
            
            let index = sender as! IndexPath
            
            userDetailVC.selectedUser = self.searchResultList[index.row]
            
        }
    }
    
    
    
}

// - MARK: - 서치바 델리게이트
extension SearchVC : UISearchBarDelegate{
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if((searchText.count) > 1){
            self.searchUser(userId: searchText)
        }
    }
    
    
}
// - MARK: - 네트워크 로직
extension SearchVC{
    
    func searchUser( userId : String){
        
        let  url = "\(self.delegate.serverIp)/search/user/\(userId)"
        
        let searchUserRequest = Alamofire.request(url, method: .get )
        
        searchUserRequest.responseJSON{
            
            res in
            
            guard let json = res.result.value as? NSDictionary else {
                
                self.alertMaker.makeAlert(title: "서버 오류", message: "서버로 부터 응답이 없습니다.", VC: self)
                
                return
            }
            
            let resultCode = json["result"] as! Int
            
            if(resultCode == 1){
                
                self.searchResultList.removeAll();
                
                
                let jsonUsers = json["users"] as! NSArray
                print(jsonUsers)
                
                for jsonUser in jsonUsers {
                    
                    let user = jsonUser as! NSDictionary
                    
                    print(user)
                    let searchedUser = UserVO()
                    
                    searchedUser._id = (user["_id"] as! String)
                    searchedUser.userId = (user["userId"] as! String)
                    searchedUser.comment = (user["comment"] as! String)
                    searchedUser.profileImgUrl = (user["profileImgUrl"] as! String)
                    searchedUser.userName = (user["userName"] as! String)
                    searchedUser.profileImg = UIImage(data: try! Data(contentsOf: URL(string: "\(self.delegate.serverIp)/\(searchedUser.profileImgUrl!)")!))
                    
                    self.searchResultList.append(searchedUser)
                }
               self.tbvUserList.reloadData()
            }else{
                self.searchResultList.removeAll()
                self.tbvUserList.reloadData()
            }
            
        }
    }
}
