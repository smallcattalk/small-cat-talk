//
//  RoomListVC.swift
//  SocketChat
//
//  Created by 이동영 on 20/03/2019.
//  Copyright © 2019 부엉이. All rights reserved.
//

import UIKit
import SocketIO


class RoomListVC: UITableViewController {
    
    
    
    var roomsList : [RoomVO] = []{
        didSet(room){
            print(roomsList)
            self.tableView.reloadData()
        }
        willSet(newValue){
            
        }
    }
    
    var toggle : Bool = false
    
    var login :Bool = false  {
        
        willSet(BEFORE){
            self.toggle = BEFORE
        }
        
        didSet(AFTER){
            if(AFTER != self.toggle ){
                
            }
        }
        
    }
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
  
    var socketManager : SocketManager?
    
    var socket : SocketIOClient?
    
    @IBAction func addRoom(_ sender: Any) {
    
        var usersList = [String]()
        usersList.append((self.delegate.currentUser?._id)!)
        
        _ = self.delegate.currentUser?.friendsList?.map{
            friend in
            let friendDic = friend as! NSDictionary
            let friendId = friendDic["_id"] as! String
            usersList.append((friendId))
        }
        
        
        let netWorkProcesser = NetworkProcesser()
        netWorkProcesser.addGroupRoom(usersList: usersList, VC: self)
        
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
           self.login = self.delegate.logIn
             let networkProcesser = NetworkProcesser()
        networkProcesser.loadRoom(_id: self.delegate.currentUser!._id!, VC: self)
       
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        self.tableView.allowsSelection = true;
        self.socketManager = self.delegate.socketManager
        //self.socketManager?.connect()
        self.socket = socketManager?.socket(forNamespace: "/room")
        self.socket?.connect()
        self.addSocketHandler()
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
      

    }

    // MARK: - Table view data source


    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return (self.roomsList.count)
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RoomCell", for: indexPath) as! RoomCell

        self.fillCell(cell: cell, roomVO: self.roomsList[indexPath.row])

        return cell
    }
    

    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        self.tableView.allowsSelection = false;
        performSegue(withIdentifier: "ChatRoomSegue", sender: indexPath.row)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let index = sender as! Int
        
        if( segue.identifier == "ChatRoomSegue"){
            let ChatRoomVC = segue.destination as! ChatRoomVC
            ChatRoomVC.currentRoomId = self.roomsList[index]._id
            ChatRoomVC.currentRoom  = self.roomsList[index]
            ChatRoomVC.friendId  = self.roomsList[index].friend?._id
            ChatRoomVC.socketManager = self.socketManager
            }
//        self.socketManager?.disconnectSocket((self.socket)!)
//        self.socketManager?.removeSocket(self.socket!)
    }
 
    @IBAction func btnAddRoom(_ sender: Any) {
        
    }
    
    func fillCell( cell : RoomCell , roomVO : RoomVO){
        
   
        if((roomVO.usersList!.count) == 2){
        cell.roomImg.image = roomVO.friend?.profileImg
        cell.roomTitle.text = roomVO.friend?.userName
        cell.unReadMeassageCount.text = "\(roomVO.unreadCount!)"
        }
        else{
            var title = ""
            print(roomVO.usersList)
            for i in 0..<3{
                title = "\(((roomVO.usersList![i]) as! UserVO).userName as! String), \(title)"
            }
            cell.roomTitle.text = "\(title)...단체대화방"
            cell.roomImg.image = UIImage.init(data: try! Data.init(contentsOf: URL.init(string: "\(self.delegate.serverIp)/\(self.delegate.defaultGroupImg)")! ))
        }
        if( roomVO.unreadCount == 0){
              cell.unReadMeassageCount.backgroundColor = UIColor.init(red: 0, green: 0, blue: 0, alpha: 0)
            cell.unReadMeassageCount.text = "1"
        }
        
    }
    
    
}
extension RoomListVC{
    func addSocketHandler(){
        self.socket?.on(clientEvent: .connect) {data, ack in
            print("socket connected")
            self.socket?.emit("init",["_id":self.delegate.currentUser?._id])
            
        }
        self.socket?.on("newRoom") {
            data, ack in
            let networkProcesser = NetworkProcesser()
            networkProcesser.loadRoom(_id: self.delegate.currentUser!._id!, VC: self)
            //self.tableView.reloadData()
        }
    }
}
