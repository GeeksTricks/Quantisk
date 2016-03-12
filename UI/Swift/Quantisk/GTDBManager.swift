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
        //https://user1:qwerty1"@api-quantisk.rhcloud.com/v1/persons/
        
        //https://api-quantisk.rhcloud.com/v1/users/
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
      //"https://user1:qwerty1@api-quantisk.rhcloud.com/v1/sites/"
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
    
    //getPersonFromID
    func getPersonNameFromId(IDPerson: Int?) -> (String){
        
        if let ID = IDPerson {
         let currentPerson = self.realm.objects(GTPersons).filter("ID == %@",ID).first
        return currentPerson!.Name
        } else{
            return ""
        }
  
    }
    //getPersonFromName
    func getPersonIDFromName(NamePerson: String) -> Int{
        let currentPerson = self.realm.objects(GTPersons).filter("Name == %@",NamePerson).first
        return currentPerson!.ID
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
    func getLoadlStat(siteId: Int) {
      //  /v1/totalrank/<site_id>/
        //  let URL =    "https:user1:qwerty1@api-quantisk.rhcloud.com/v1/totalrank/1/"
        let URL =    "https://" + self.getCurrentLogin() + ":" + self.getCurrentPassword() + "@api-quantisk.rhcloud.com/v1/totalrank/" + String(siteId) + "/"
        print(URL)
     
        let stats = self.realm.objects(GTStat)
        try! realm.write {
            realm.delete(stats)
        }
        
         Alamofire.request(.GET, URL , parameters: nil).responseJSON {
            response in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                  
                    for (_,subJson):(String, JSON) in json {
                        print(subJson["rank"].int!," ",subJson["person_id"].int!," ",self.getPersonNameFromId(subJson["person_id"].int!)," ")
                      
                        let stat = GTStat()
                        stat.person = self.getPersonNameFromId(subJson["person_id"].int!)
                        stat.stat = subJson["rank"].int!
                      
                       
                        try! self.realm.write {
                            self.realm.add(stat)
                        }
                   
                    }
                    
                    NSNotificationCenter.defaultCenter().postNotificationName("NotificationIdentifierStat", object: nil)
                }
            case .Failure(let error):
                print(error)
            }
        }
       
    }
    func getLoadlStatPersonId(siteId: Int, personId: Int, startDate: String , endDate: String) {
     /*
        /v1/dailyrank/?person_id=<person_id>&site_id=<site_id>&start_date=<start_date>&end_date=<end_date>
    https://user1:qwerty1@api-quantisk.rhcloud.com/v1/dailyrank/?site_id=1&person_id=1&start_date=2016-02-01&end_date=2016-02-11
        
        GET: Возвращает статистику по дням для конкретной личности на конкретном сайте в промежутке между двумя датами.
        Принимает аргументы в урле. Даты ожидаются в ISO-формате (например: 2005-08-09T18:31:42). Таймзоны пока не работают.
        Возвращает:
        [
        {
        "rank": integer,
        "day": string,
        "site_id": integer,
        "person_id": integer,
        }
        ]
        */
        
        
       let URL =    "https://" + self.getCurrentLogin() + ":" + self.getCurrentPassword() + "@api-quantisk.rhcloud.com/v1/dailyrank/"
        
        print(URL)
        
        let stats = self.realm.objects(GTStat)
        try! realm.write {
            realm.delete(stats)
        }
        let param: [String: AnyObject] = ["site_id":siteId,"person_id":personId,"start_date":startDate,"end_date":endDate]
        Alamofire.request(.GET, URL , parameters: param  ).responseJSON {
            response in
            
            print(response.request)  // original URL request
            print(response.response) // URL response
            print(response.data)     // server data
            print(response.result)   // result of response serialization
            
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                    print("enter")
                    for (_,subJson):(String, JSON) in json {
    //                    print(subJson["rank"].int!)//," ",subJson["person_id"].int!," ",self.getPersonNameFromId(subJson["person_id"].int!)," ",subJson["day"].int!)
                        
                        let stat = GTStat()
                        if self.getPersonNameFromId(subJson["person_id"].int) == ""{
                          continue
                        } else{
                        stat.person = self.getPersonNameFromId(subJson["person_id"].int!)
                            
                        
                        stat.stat = subJson["rank"].int!
                        stat.date = subJson["day"].string!
                        
                      
                        try! self.realm.write {
                            self.realm.add(stat)
                        }
                        }
                        
                    }
                    
                    NSNotificationCenter.defaultCenter().postNotificationName("NotificationIdentifierStat", object: nil)
                }
            case .Failure(let error):
                print(error)
            }
        }
        
    }
    
    //getTotalStat
    func getTotalStatText() ->([String]){
        
        let text = self.realm.objects(GTStat)
        
        var text_list: [String] = []
        for var i = 0; i < text.count; i++ {
            print(text[i].person)
            text_list.append(text[i].person)
        }
        return text_list
    }
    //getTotalStat
    func getTotalStatSubText(type:Int) ->([String]){
        
        let text = self.realm.objects(GTStat)
        
        var text_list: [String] = []
        for var i = 0; i < text.count; i++ {
            if type == 0 {
            print(String(text[i].date))
            text_list.append(String(text[i].date) + " number of articles " + String(text[i].stat)  )
            } else{
            text_list.append(String(text[i].stat)  )
            }
        }
        return text_list
    }
    
    //http://user1:qwerty1@api-quantisk.rhcloud.com/v1/dailyrank/?site_id=1&person_id=1&start_date=2006-02-11&end_date=2026-02-11
    //https://user1:qwerty1@api-quantisk.rhcloud.com/v1/persons/
    
    
  
    
    
    
    

    }
    
