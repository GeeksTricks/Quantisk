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
    //delete all pesons
    func deleteAllPersons()->(Bool){
        return true
    }
    //delete all sites
    func deleteAllSites()->(Bool){
        return true
    }
    //get refresh pesons
    func refreshPersons()->(Bool){
        return true
    }
    //get refresh sites
    func refreshSites()->(Bool){
        return true
    }
    
    
    
    //get all pesons
    func getAllPersons()->([String]){
        return true
    }
    //get all sites
    func getAllSites()->([String]){
        return true
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
    
