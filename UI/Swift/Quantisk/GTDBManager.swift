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
        let persons = self.realm.objects(GTPersons)
        try! realm.write {
            realm.delete(persons)
        }
    
    }
    //delete all sites
    func deleteAllSites(){
        let sites = self.realm.objects(GTSites)
        try! realm.write {
            realm.delete(sites)
        }
    }
    

    //get refresh pesons
    func refreshPersons()->(Bool){
        
        self.deleteAllPersons()
        
        
        // Get the default Realm
        
             let URL = "https://" + self.getCurrentLogin() + ":" + self.getCurrentPassword() + "@api-quantisk.rhcloud.com/v1/persons/"
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
        self.deleteAllSites()
      
        let URL =    "https://" + self.getCurrentLogin() + ":" + self.getCurrentPassword() + "@api-quantisk.rhcloud.com/v1/sites/"
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
    
    
    // get login
    func getCurrentLogin() -> String{
        let currentLogin = self.realm.objects(GTSetting).filter("ID == 'user'").first
       return currentLogin!.Value
     
    }
    
    // get login
    func getCurrentPassword() -> String{
        let currentPass = self.realm.objects(GTSetting).filter("ID == 'pass'").first
        return currentPass!.Value
        
    }
    
    
    // getTotalStat
   // get
    
    
    //http://user1:qwerty1@api-quantisk.rhcloud.com/v1/dailyrank/?site_id=1&person_id=1&start_date=2006-02-11&end_date=2026-02-11
    //https://user1:qwerty1@api-quantisk.rhcloud.com/v1/persons/
    
    
  
    
    
    
    

    }
    
