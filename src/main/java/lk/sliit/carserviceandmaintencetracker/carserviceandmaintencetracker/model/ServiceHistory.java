package com.app.carmaintenance.car_maintenance.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ServiceHistory {
    private Node head;

    private class Node {
        ServiceRecord data;
        Node next;
        Node(ServiceRecord data) {
            this.data = data;
        }
    }

    public void add(ServiceRecord record) {
        Node newNode = new Node(record);
        newNode.next = head;
        head = newNode;
    }

    public List<ServiceRecord> getAll() {
        List<ServiceRecord> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }


    public void selectionSortByDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Node i = head; i != null; i = i.next) {
            Node min = i;
            LocalDate minDate = null;

            try {
                minDate = LocalDate.parse(min.data.getDate().trim(), formatter);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            for (Node j = i.next; j != null; j = j.next) {
                try {
                    LocalDate dateJ = LocalDate.parse(j.data.getDate().trim(), formatter);
                    if (dateJ.isBefore(minDate)) {
                        min = j;
                        minDate = dateJ;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (min != i) {
                ServiceRecord temp = i.data;
                i.data = min.data;
                min.data = temp;
            }
        }
    }
}

