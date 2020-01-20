#!/usr/bin/env bash

curl -H 'Content-Type: application/json' 'http://localhost:8983/solr/categories/update?commit=true' --data-binary '[
  {"id":"root", "is_active_i":1, "out_edge_ss":["Bed & Bath", "Furniture", "Kitchen"] },
  {"id":"Bed & Bath","is_active_i":1, "out_edge_ss":["Bedding Collections","Sheets"] },
  {"id":"Bedding Collections","is_active_i":1, "out_edge_ss":["Sateen"] },
  {"id":"Sheets","is_active_i":1, "out_edge_ss":["Sateen", "Legacy Cotton"] },
  {"id":"Sateen","is_active_i":1},
  {"id":"Legacy Cotton","is_active_i":0},
  {"id":"Furniture", "is_active_i":1, "out_edge_ss":["All Furniture"] },
  {"id":"All Furniture", "is_active_i":1, "out_edge_ss":["Beds & Headboards", "Couches & Sofas", "Dining Room Sets"] },
  {"id":"Beds & Headboards", "is_active_i":1, "out_edge_ss":["IKEA"] },
  {"id":"Couches & Sofas", "is_active_i":1, "out_edge_ss":["IKEA"] },
  {"id":"Dining Room Sets", "is_active_i":1, "out_edge_ss":["IKEA"] },
  {"id":"IKEA", "is_active_i":1},
  {"id":"Kitchen","is_active_i":1, "out_edge_ss":["Bakeware","Coffee Makers", "Air Fryers"] },
  {"id":"Bakeware","is_active_i":1, "out_edge_ss":["Electronics"] },
  {"id":"Coffee Makers","is_active_i":1, "out_edge_ss":["Electronics"] },
  {"id":"Air Fryers","is_active_i":1, "out_edge_ss":["Electronics"] },
  {"id":"Electronics","is_active_i":1, "out_edge_ss":["Small Appliances", "Big Appliances"] },
  {"id":"Small Appliances","is_active_i":1, },
  {"id":"Big Appliances","is_active_i":1}
  ]'




