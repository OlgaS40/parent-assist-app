import { AfterViewInit, Component, ViewChild, inject } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import {MatSnackBar, MatSnackBarRef} from '@angular/material/snack-bar';
import { Role, User } from 'src/app/shared/model/user';
import { UsersService } from 'src/app/shared/service/users.service';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements AfterViewInit {
  displayedColumns: string[] = [
    "position",
    "username",
    "email",
    "roles",
    "status",
    "enable",
    "actions"
  ];
  dataSource!: MatTableDataSource<any>;
  userRole = Role;
  userRoles!: { name: string; value: string; }[];
  durationInSeconds = 5;
  selection = new SelectionModel<any>(true, []);


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _snackBar: MatSnackBar,
    private _dialog: MatDialog,
    private _usersService: UsersService,
  ) {}


  ngAfterViewInit(): void {
    this.getAllUsers();
    this.userRoles = Object.entries(this.userRole).map(([key, value]) => ({
     name: key,
     value: value,
   }));
  }
 
  getAllUsers() {
    this._usersService.getAllUsers().subscribe({
      next: (res) => {
        console.log(res);
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: () =>
        console.log("An error has occurred while fetching data from database"),
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  createUser() {
    const dialogRef = this._dialog.open(CreateUserComponent, {
      width: "75%",
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === "save") {
        this.getAllUsers();
      }
    });
  }

  editUser(row: any) {
    this._dialog.open(CreateUserComponent, {
        width: "75%",
        data: row,
      })
      .afterClosed()
      .subscribe((result) => {
        console.log(`Dialog result: ${result}`);
        if (result === "update") {
          this.getAllUsers();
          // TODO - custom notification message
          this._snackBar.open("Edited successfully", "OK");
        }
      });
  }

  deleteUser(row: User) {
    this._dialog.open(ConfirmDialogComponent, {
      // width: '75%',
      data: row
    }).afterClosed().subscribe(confirm => {
      if (confirm && row.id !== undefined) {
        this._usersService.deleteUser(row.id).subscribe({
          next: (res) => {
            // TODO - custom notification message
            this._snackBar.open("Deleted successfully", "OK");
            this.getAllUsers();
          },
          error: () => {
            alert("Error while deleting user");
          }
        });
      }
    });
  }

  enableUser(row: any){

  }

}


