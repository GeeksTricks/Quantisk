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
        
        let URL = "http://api-quantisk.rhcloud.com/v1/persons/"
        Alamofire.request(.GET, URL , parameters: nil).responseJSON {
            response in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                    print(json)
                    
                    
                    
                }
            case .Failure(let error):
                print(error)
            }
            
        }
        
        
        return true
    }
    //get refresh sites
    func refreshSites()->(Bool){
        return true
    }
    
    
    
    //get all pesons
    func getAllPersons()->([String]){
        
    

        
            var persons: [String] = ["Ivanov", "Petrov", "Sidorov"]
        return persons
    }
    //get all sites
    func getAllSites()->([String]){
        var site:[String] = ["Lenta.ru","News.ru","Vesti.ru"]
        return site
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
    
