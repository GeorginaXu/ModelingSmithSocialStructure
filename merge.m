%merge u and v into a supernode, meaning clearing edges between nodes 
function [supernodes,A]=merge(u,v,supernodes,A)

%nodes that belong to the same group as node u
groupu=find(supernodes==supernodes(u));
%nodes that belong to the same group as node v
groupv=find(supernodes==supernodes(v));
%remove edges between nodes in groupu and nodes in groupv
A(groupu,groupv)=0;
A(groupv,groupu)=0;

%assign nodes in groupv to the group that u belongs to
supernodes(groupv)=supernodes(u);

% x=cat(2,u,v);%concat u and v to create a new supernode x
% for i=1:length(supernodes)
%     if supernodes{i}~=u && supernodes{i}~=v
% 
%     end
% end
% supernodes{u}=[];
% supernodes{v}=[];
% supernodes{length(supernodes)+1}=x;
% supernodes=superbides(~cellfun('isempty',supernodes)); 
