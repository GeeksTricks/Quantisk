//
//  GTDBManager.swift
//  Quantisk
//
//  Created by iMac on 13.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import UIKit
import RealmSwift
import Alamofire
import SwiftyJSON

class GTDBManager{
//    #mark delete

    //singlenton
    static let sharedInstance = GTDBManager()
    let realm = try! Realm()
    
    //delete all pesons
    func deleteAllPersons(){
        let realm = try! Realm()
        realm.deleteAll()
    
    }
    //delete all sites
    func deleteAllSites(){
        let realm = try! Realm()
        realm.deleteAll()
    }
    

    //get refresh pesons
    func refreshPersons()->(Bool){
        // Get the default Realm
        
             let URL = "https://user1:qwerty1@api-quantisk.rhcloud.com/v1/persons/"
        Alamofire.request(.GET, URL , parameters: nil).responseJSON {
            response in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                  
                    for (_,subJson):(String, JSON) in json {
                       print(subJson["id"].int!," ",subJson["name"].string!)
                        let person = GTPersons()
                        person.ID = subJson["id"].int!
                        person.Name = subJson["name"].string!
                        
                        // Persist your data easily
                        try! self.realm.write {
                            self.realm.add(person)
                        }
                        
                    }
                   
                }
            case .Failure(let error):
                print(error)
            }
       }
        
        return true
    }
    //get refresh sites
    func refreshSites()->(Bool){
        
        
        let URL = "https://user1:qwerty1@api-quantisk.rhcloud.com/v1/sites/"
        Alamofire.request(.GET, URL , parameters: nil).responseJSON {
            response in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                    for (_,subJson):(String, JSON) in json {
                        print(subJson["id"].int!," ",subJson["name"].string!)
                        let site = GTSites()
                        site.ID = subJson["id"].int!
                        site.Name = subJson["name"].string!
                        
                        // Persist your data easily
                        try! self.realm.write {
                            self.realm.add(site)
                        }
                        
                    }
                }
            case .Failure(let error):
                print(error)
            }
        }
        return true

    }
    
    
    
    //get all pesons
    func getAllPersons()->([String]){
        
    

        let persons = self.realm.objects(GTPersons)
     
        var persons_list: [String] = []
        for var i = 0; i < persons.count; i++ {
            persons_list.append(persons[i].Name)
        }
        return persons_list
    }
    //get all sites
    func getAllSites()->([String]){
        let sites = self.realm.objects(GTSites)
        
        var sites_list: [String] = []
        for var i = 0; i < sites.count; i++ {
            sites_list.append(sites[i].Name)
        }
        return sites_list
    }
    
    
    
    
    
    
    
    
    
    /*
    
    
    
    
    
    func general()->(){
        super.viewDidLoad()
        let realm = try! Realm()
        let onlineLW = Data()
        let ot = temp()
        let URL = "http://api.openweathermap.org/data/2.5/forecast?"
        let param = ["q": "Moscow", "appid": "cc43de317c7b45042d6dd7d09ee12d74"]
        Alamofire.request(.GET, URL , parameters: param)
            .responseJSON { response in
                switch response.result {
                case .Success:
                    if let value = response.result.value {
                        let json = JSON(value)
                        print(json)
                        onlineLW.city_n = json["city"]["name"].stringValue
                        print(onlineLW.city_n)
                        onlineLW.update_d = NSDate()
                        for (_,subJson):(String, JSON) in json["list"] {
                            ot.t = subJson["main"]["temp"].double!
                            onlineLW.templst.append(ot)
                        }
                        print(onlineLW.templst[0].t)
                        try! realm.write {
                            realm.add(onlineLW, update: true)
                            
                        }
                        
                    }
                case .Failure(let error):
                    print(error)
                }
                
                
        }*/
        
        // Do any additional setup after loading the view, typically from a nib.
    }
    
