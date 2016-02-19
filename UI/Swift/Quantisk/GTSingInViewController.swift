//
//  GTSingInViewController.swift
//  Quantisk
//
//  Created by iMac on 09.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import RealmSwift
import UIKit

class GTSignInViewController: UIViewController {
    @IBAction func logIn(sender: AnyObject) {
     
        // Get the default Realm
        let realm = try! Realm()
        
        let username = GTSetting()
        username.ID = "user"
        username.Value = self.textUserName.text!
        
        let password = GTSetting()
        password.ID = "pass"
        password.Value = self.textPassword.text!
        
         let currentLogin = realm.objects(GTSetting).filter("ID == 'user'").first
         let currentPass = realm.objects(GTSetting).filter("ID == 'pass'").first
        
        
        if currentLogin != nil{
            try! realm.write {
                currentLogin?.Value = self.textUserName.text!
            }
        }else{
            try! realm.write {
                realm.add(username)
            }
        }
        if currentPass != nil{
            try! realm.write {
                currentPass?.Value = self.textPassword.text!
            }
        }else{
            try! realm.write {
                realm.add(password)
            }
        }
        
     
        
    }
    @IBOutlet var textUserName: UITextField!
    @IBOutlet var textPassword: UITextField!
    override func viewDidLoad() {
       
    
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}
